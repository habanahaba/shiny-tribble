(ns fchallenge.utils-test
  (:require [fchallenge.utils :refer :all]
            [clojure.test :refer :all]))

(deftest utils-test
  (testing "domain trimming for one-part public suffix"
    (let [trimmed-domain (trim-domain "foo.bar.com")]
      (is (= trimmed-domain "bar.com"))))

  (testing "domain trimming for two-part public suffix"
    (let [trimmed-domain (trim-domain "foo.co.uk")]
      (is (= trimmed-domain "foo.co.uk"))))

  (testing "host name extraction"
    (let [host (parse-domain "http://meh.acme.foo.bar?query=bazz&param=fizz") ]
      (is (= host "meh.acme.foo.bar")))
    ))