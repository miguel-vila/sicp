(ns sicp.chapter2-ex29-test
  (:require [sicp.chapter2_ex29 :refer :all])
  (:use midje.sweet))

(def mobile1 (make-mobile
              (make-branch 3 [:weight 4])
              (make-branch 5 [:weight 2])))

(def mobile2 (make-mobile
              (make-branch 3 [:weight 4])
              (make-branch 5 [:mobile (make-mobile
                                       (make-branch 2 [:weight 3])
                                       (make-branch 4 [:weight 2]))])))

(facts "total-weight"
       (total-weight mobile1) => 6
       (total-weight mobile2) => 9)
