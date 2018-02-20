(ns fchallenge.utils
  (:import [java.net URL]
           [com.google.common.net InternetDomainName]))

(defn parse-domain [url]
  (.getHost (URL. url)))

(defn trim-domain [hostname]
  (-> hostname
      (InternetDomainName/from)
      (.topPrivateDomain)
      (.toString)))
