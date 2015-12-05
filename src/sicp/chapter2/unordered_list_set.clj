(ns sicp.chapter2.unordered_list_set
  (:require [clojure.core.match :refer [match]]
            [sicp.chapter2.set :refer :all]))

(extend-type clojure.lang.IPersistentList
  Set

  ; ex 2.59
  (union-set
   [l1 l2]
   (if
     (empty? l1) l2
     (let [new-elem (first l1)
           elem (element-of-set? l2 new-elem)]
       (if elem
         (union-set (rest l1) l2)
         (cons new-elem (union-set (rest l1) l2))))))

  (intersection-set
   [l1 l2]
   (cond
    (or (empty? l1) (empty? l2))    '()
    (element-of-set? l2 (first l1)) (cons (first l1) (intersection-set (rest l1) l2))
    :otherwise                      (intersection-set (rest l1) l2)))

  (element-of-set?
   [l e]
   (cond
    (empty? l) false
    :otherwise (if (= (first l) e) true (element-of-set? (rest l) e))))

  (adjoin-set
   [l e]
   (if (element-of-set? l e)
     l
     (cons e l))))

