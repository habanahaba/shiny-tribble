(ns fchallenge.search
  (:require [fchallenge.utils :refer [parse-domain trim-domain]]
            [clojure.string :as string]
            [feedparser-clj.core :as feed])
  (:import [java.net URLEncoder]))

(def SEARCH-URL-FORMAT "https://www.bing.com/search?format=rss&count=10&q=%s")

(defn- search-url [terms]
  (format SEARCH-URL-FORMAT (URLEncoder/encode (string/join "&" terms))))

(defn- feed-stats [entries]
  (into {}
    (->> entries
         (map #(-> % :link parse-domain))
         (distinct)
         (map trim-domain)
         (frequencies))))

(defn bing-search [& query]
  (let [url (search-url query)]
    (feed-stats (:entries (feed/parse-feed url)))))
