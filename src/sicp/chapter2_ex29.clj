(ns sicp.chapter2_ex29
  (:require [clojure.core.typed :as t]
            [clojure.core.match :refer [match]]))

; Type definitions
(t/defalias Mobile '{:left Branch, :right Branch})

(t/defalias Weight Number)

(t/defalias Structure
  (t/U '[(t/Value :weight) Weight]
       '[(t/Value :mobile) Mobile]))

(t/defalias Branch '{:length Number, :structure Structure})

; Constructors
(t/ann make-mobile [Branch Branch -> Mobile])
(defn make-mobile [left right]
  {:left left :right right})

(t/ann make-branch [Number Structure -> Branch])
(defn make-branch [length structure]
  {:length length :structure structure})

; Getters
(t/ann left-branch [Mobile -> Branch])
(defn left-branch [mobile]
  (:left mobile))

(t/ann right-branch [Mobile -> Branch])
(defn right-branch [mobile]
  (:right mobile))

(t/ann branch-length [Branch -> Number])
(defn branch-length [branch]
  (:length branch))

(t/ann branch-structure [Branch -> Structure])
(defn branch-structure [branch]
  (:structure branch))

; Total weight
(t/ann total-weight [Mobile -> Number])
(defn total-weight [mobile]
  (t/letfn> [structure-weight :- [Structure -> Number]
             (structure-weight [structure]
                               (match structure
                                        [:weight weight] weight
                                        [:mobile _mobile] (total-weight _mobile)))
             branch-weight :- [Branch -> Number]
             (branch-weight [branch]
                            (structure-weight (branch-structure branch)))]
            (let
              [left (branch-weight (left-branch mobile))
               right (branch-weight (right-branch mobile))]
              (+ left right))))
