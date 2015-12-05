(ns sicp.chapter2.unordered-list-set-test
  (:require [sicp.chapter2.unordered_list_set :refer :all]
            [sicp.chapter2.set :refer :all])
  (:use midje.sweet))

(facts "union-set unordered lists"
       (union-set '(1 2 3) '(4 5 6)) => '(1 2 3 4 5 6)
       (union-set '(1) '(1 5 6)) => '(1 5 6)
       (union-set '(1 3 4) '(1 2 3 5 6)) => '(4 1 2 3 5 6))

(facts "intersection-set unordered lists"
       (intersection-set '(1 2) '(3 4)) => '()
       (intersection-set '(1 2) '(1 4)) => '(1)
       (intersection-set '(1 2 3) '(4 3 2 1)) => '(1 2 3))

