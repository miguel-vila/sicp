(ns sicp.chapter2.ex29
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

(declare structure-weight)
(t/ann total-weight [Mobile -> Number])
(defn total-weight [mobile]
  (t/letfn> [branch-weight :- [Branch -> Number]
             (branch-weight [branch]
                            (structure-weight (branch-structure branch)))]
            (+ (branch-weight (left-branch mobile))
               (branch-weight (right-branch mobile)))))

(t/ann structure-weight [Structure -> Number])
(defn structure-weight [structure]
  (match structure
         [:weight weight] weight
         [:mobile mobile] (total-weight mobile)))

(declare balanced?)
(t/ann balanced-structure? [Structure -> Boolean])
(defn balanced-structure? [structure]
  (match structure
         [:weight _] true
         [:mobile _mobile] (balanced? _mobile)))

(t/ann balanced? [Mobile -> Boolean])
(defn balanced? [mobile]
  (let [left-b (left-branch mobile)
        right-b (right-branch mobile)
        left-s (branch-structure left-b)
        right-s (branch-structure right-b)]
    (and
     (balanced-structure? left-s)
     (balanced-structure? right-s)
     (=
      (* (branch-length left-b) (structure-weight left-s))
      (* (branch-length right-b) (structure-weight right-s))))))
