(ns fchallenge.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [fchallenge.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/search?query=something"))]
      (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
