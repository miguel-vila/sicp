(ns sicp.chapter2.ordered-list-set-test
  (:require [sicp.chapter2.ordered_list_set :refer :all]
            [sicp.chapter2.set :refer :all])
  (:use midje.sweet))

(facts "adjoin-set ordered lists"
       (adjoin-set '() 1) => '(1)
       (adjoin-set '(1 3 4) 2) => '(1 2 3 4)
       (adjoin-set '(1 3 4) 5) => '(1 3 4 5)
       (adjoin-set '(1 3 4) 0) => '(0 1 3 4)
       )

(facts "union-set ordered lists"
       (union-set '(1 2 3) '(4 5 6)) => '(1 2 3 4 5 6)
       (union-set '(1) '(1 5 6)) => '(1 5 6)
       (union-set '(1 3 4) '(1 2 3 5 6)) => '(1 2 3 4 5 6)
       )

(facts "intersection-set ordered lists"
       (intersection-set '(1 2) '(3 4)) => '()
       (intersection-set '(1 2) '(1 4)) => '(1)
       (intersection-set '(1 2 3) '(1 2 3 4 5 6)) => '(1 2 3)
       (intersection-set '(7 9 12) '(1 8 9 12 13)) => '(9 12)
       )

