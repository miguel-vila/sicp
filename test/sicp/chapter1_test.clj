(ns sicp.chapter1-test
  (:require [sicp.chapter1 :refer :all])
  (:use midje.sweet))

(facts "square-sum-biggest"
       (square-sum-biggest 6 5 4) => 61
       (square-sum-biggest 2 7 3) => 58
       (square-sum-biggest 2 1 3) => 13
       (square-sum-biggest 5 6 3) => 61)

(facts "f-rec"
       (f-rec 0) => 0
       (f-rec 1) => 1
       (f-rec 2) => 2
       (f-rec 3) => 4
       (f-rec 4) => 11
       (f-rec 5) => 25)

(facts "f-iter"
       (f-iter 0) => 0
       (f-iter 1) => 1
       (f-iter 2) => 2
       (f-iter 3) => 4
       (f-iter 4) => 11
       (f-iter 5) => 25)

(facts "pascal"
       (pascal 0 0) => 1
       (pascal 1 0) => 1
       (pascal 1 1) => 1

       (pascal 2 0) => 1
       (pascal 2 1) => 2
       (pascal 2 2) => 1

       (pascal 3 0) => 1
       (pascal 3 1) => 3
       (pascal 3 2) => 3
       (pascal 3 3) => 1

       (pascal 4 0) => 1
       (pascal 4 1) => 4
       (pascal 4 2) => 6
       (pascal 4 3) => 4
       (pascal 4 4) => 1

       (pascal 5 0) => 1
       (pascal 5 1) => 5
       (pascal 5 2) => 10
       (pascal 5 3) => 10
       (pascal 5 4) => 5
       (pascal 5 5) => 1)
