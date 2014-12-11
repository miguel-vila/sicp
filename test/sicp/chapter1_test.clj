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

(facts "fast-expn-iter"
       (fast-expn-iter 2 0) => 1
       (fast-expn-iter 2 1) => 2
       (fast-expn-iter 2 2) => 4
       (fast-expn-iter 2 3) => 8
       (fast-expn-iter 2 4) => 16

       (fast-expn-iter 10 0) => 1
       (fast-expn-iter 10 1) => 10
       (fast-expn-iter 10 2) => 100
       (fast-expn-iter 10 3) => 1000
       (fast-expn-iter 10 4) => 10000
       (fast-expn-iter 10 5) => 100000
       (fast-expn-iter 10 6) => 1000000

       (fast-expn-iter 3 5) => 243)

(facts "fast-mult-rec"
       (fast-mult-rec 3 1) => 3
       (fast-mult-rec 1 3) => 3
       (fast-mult-rec 10 200) => 2000
       (fast-mult-rec 5 6) => 30
       (fast-mult-rec 1 10) => 10
       (fast-mult-rec 10 1) => 10)

(facts "fast-mult-iter"
       (fast-mult-iter 3 1) => 3
       (fast-mult-iter 1 3) => 3
       (fast-mult-iter 10 200) => 2000
       (fast-mult-iter 5 6) => 30
       (fast-mult-iter 1 10) => 10
       (fast-mult-iter 10 1) => 10)

(facts "square-mod-n"
       (square-mod-n 3 8) => 1
       (square-mod-n 2 7) => 4
       (square-mod-n 4 16) => 0)

(facts "expmod"
       (expmod 2 3 7) => 1
       (expmod 2 4 20) => 16
       (expmod 5 3 20) => 5
       (expmod 1 1000 43) => 1
       (expmod 3 5 243) => 0
       (expmod 3 4 80) => 1)
