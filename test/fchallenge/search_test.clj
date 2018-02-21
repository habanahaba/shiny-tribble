(ns fchallenge.search-test
  (:require [clojure.test :refer :all]
            [fchallenge.search :refer :all]))

(deftest search-test
  (testing "search url formation"
    (let [url (search-url "shipilev")]
      (is (= url "https://www.bing.com/search?format=rss&count=10&q=shipilev"))))

  (testing "search url with url-encode"
    (let [url (search-url "escape me")
          url1 (search-url "5 > 1")]
      (is (= url "https://www.bing.com/search?format=rss&count=10&q=escape+me")
      (is (= url1 "https://www.bing.com/search?format=rss&count=10&q=5+%3E+1")))))

  (testing "feed stats"
    (let [stats (feed-stats ["http://foo.com", "http://bazz.com", "http://acme.foo.com", "http://acme.foo.com"])]
      (is (= (get stats "foo.com") 2))
      (is (= (get stats "bazz.com") 1))
      (is (nil? (get stats "here.is.no.me"))))))