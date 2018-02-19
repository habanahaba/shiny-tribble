(ns fchallenge.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [fchallenge.search :refer [bing-search]]))

(defroutes app-routes
  (GET "/search" {{:keys [query]} :params} (response (bing-search query)))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-response {:pretty true})
      (wrap-defaults site-defaults)))
