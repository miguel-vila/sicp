(ns sicp.chapter2.set
  (:require [clojure.core.match :refer [match]]))

(defprotocol Set
  (union-set [s1 s2])
  (intersection-set [s1 s2])
  (element-of-set? [s e])
  (adjoin-set [s e]))
