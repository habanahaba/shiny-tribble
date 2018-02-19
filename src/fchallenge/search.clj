(ns fchallenge.search
  (:require [clojure.string :as string]
            [feedparser-clj.core :as feed])
  (:import (java.net URLEncoder URL)))

(def SEARCH-URL-FORMAT "https://www.bing.com/search?format=rss&count=10&q=%s")

(defn- search-url [terms]
  (format SEARCH-URL-FORMAT (URLEncoder/encode (string/join "&" terms))))

(defn- parse-domain [url]
  (.getHost (URL. url)))

(defn- feed-stats [entries]
  (into {}
    (frequencies
      (map #(-> % :link parse-domain) entries))))

(defn bing-search [& query]
  (let [url (search-url query)]
    (feed-stats (:entries (feed/parse-feed url)))))
