(ns sicp.chapter2_ex30
  (:require [clojure.core.typed :as t]
            [clojure.core.match :refer [match]]))

(t/defalias Tree
  (t/U '[(t/Value :leaf) Number]
       '[(t/Value :node) Tree Tree]))

(t/ann make-leaf [Number -> Tree])
(defn make-leaf [number]
  [:leaf number])

(t/ann make-node [Tree Tree -> Tree])
(defn make-node [left right]
  [:node left right])

(t/ann square-tree [Tree -> Tree])
(defn square-tree [tree]
  (match tree
         [:leaf leaf] [:leaf (* leaf leaf)]
         [:node left right] [:node (square-tree left) (square-tree right)]))

(t/ann map-tree [Tree [Number -> Number] -> Tree])
(defn map-tree [tree f]
  (match tree
         [:leaf leaf] [:leaf (f leaf)]
         [:node left right] [:node (map-tree left f) (map-tree right f)]))

(t/ann square [Number -> Number])
(defn square [x] (* x x))

(t/ann square-tree-using-map [Tree -> Tree])
(defn square-tree-using-map [tree]
  (map-tree tree square))
