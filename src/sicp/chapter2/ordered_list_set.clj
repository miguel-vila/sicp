(ns sicp.chapter2.ordered_list_set
  (:require [clojure.core.match :refer [match]]
            [sicp.chapter2.set :refer :all]))

(extend-type clojure.lang.IPersistentList
  Set

  (union-set
   [l1 l2]
   (cond
    (empty? l1) l2
    (empty? l2) l1
    :otherwise
      (let [x1 (first l1)
            x2 (first l2)]
        (cond
         (= x1 x2)   (cons x1 (union-set (rest l1) (rest l2)))
         (< x1 x2)   (cons x1 (union-set (rest l1) l2))
         (> x1 x2)   (cons x2 (union-set l1 (rest l2)))))))

  (intersection-set
   [l1 l2]
   (if (or (empty? l1) (empty? l2))   '()
     (let [x1 (first l1)
           x2 (first l2)]
       (cond
        (= x1 x2)   (cons x1 (intersection-set (rest l1) (rest l2)))
        (< x1 x2)   (intersection-set (rest l1) l2)
        (> x1 x2)   (intersection-set l1 (rest l2))))))

  (element-of-set?
   [l e]
   (cond
    (empty? l)       false
    (= e (first l))  true
    (< e (first l))  false
    :otherwise       (element-of-set? (rest l) e)))

  (adjoin-set
   [l e]
   (cond
    (empty? l)       (list e)
    (= e (first l))  l
    (< e (first l))  (cons e l)
    :otherwise       (cons (first l) (adjoin-set (rest l) e)))))

