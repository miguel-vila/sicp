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

(def mobile3 (make-mobile
              (make-branch 3 [:weight 4])
              (make-branch 3 [:weight 4])))

(def mobile4 (make-mobile
              (make-branch 3 [:weight 4])
              (make-branch 2 [:mobile (make-mobile
                                       (make-branch 7 [:weight 3])
                                       (make-branch 7 [:weight 3]))])))

(facts "total-weight"
       (total-weight mobile1) => 6
       (total-weight mobile2) => 9
       (total-weight mobile3) => 8
       (total-weight mobile4) => 10)

(facts "balanced?"
       (balanced? mobile1) => false
       (balanced? mobile2) => false
       (balanced? mobile3) => true
       (balanced? mobile4) => true)
