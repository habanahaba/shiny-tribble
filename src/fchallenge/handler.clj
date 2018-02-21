(ns fchallenge.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [fchallenge.search :as search]))

(defn app-routes [search-handle]
  (compojure.core/routes
    (GET "/search" {{:keys [query]} :params}
      (response (search/bing-search search-handle query)))
    (route/not-found "Not Found")))

(def app
  (let [s (search/create)]
    (do
      (try
        (-> (app-routes s)
            (wrap-json-response {:pretty true})
            (wrap-defaults site-defaults))))))
