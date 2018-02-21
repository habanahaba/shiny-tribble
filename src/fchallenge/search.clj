(ns fchallenge.search
  (:require [fchallenge.utils :refer [parse-domain trim-domain]]
            [feedparser-clj.core :as feed])
  (:import [java.net URLEncoder]
           [java.util.concurrent ThreadPoolExecutor TimeUnit LinkedBlockingQueue ThreadPoolExecutor$CallerRunsPolicy]))

(declare destroy)

(def SEARCH-URL-FORMAT "https://www.bing.com/search?format=rss&count=10&q=%s")

(defn search-url [term]
  (format SEARCH-URL-FORMAT (URLEncoder/encode term)))

(defn feed-stats [entries]
  (into {}
    (->> entries
         (distinct)
         (map parse-domain)
         (map trim-domain)
         (frequencies))))

(defn- new-thread-pool []
  (let [queue (LinkedBlockingQueue. 20)
        executor (ThreadPoolExecutor. 0 10 1 TimeUnit/MINUTES queue)]
    (do
     (.setRejectedExecutionHandler executor (ThreadPoolExecutor$CallerRunsPolicy.))
     executor)))

(defn create []
  (let [handle {:thread-pool (new-thread-pool)}]
    ; Abhorrent. Unfortunately, no other obvious way to do it better for now (component? mount?)
    (.addShutdownHook (Runtime/getRuntime) (Thread. (fn [] (destroy handle) )))
    handle))

(defn destroy [s]
  (let [{:keys [^ThreadPoolExecutor thread-pool]} s]
    (do
      (.shutdownNow thread-pool))
      (.awaitTermination thread-pool 5 TimeUnit/SECONDS)))

(defn- search-single [term]
  (->> term
       (search-url)
       (feed/parse-feed)
       (:entries)
       (map :link)))

(defn bing-search [s & terms]
  (let [ts (flatten terms)
        pool (:thread-pool s)
        tasks (map
                (fn [term]
                  (fn []
                    (search-single term)))
                ts)
        futures (.invokeAll pool tasks)]
    (->> futures
         (map #(.get %))
         (flatten)
         (feed-stats))))
