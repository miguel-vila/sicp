(ns sicp.chapter2_ex56
  (:require [clojure.core.typed :as t]
            [clojure.core.match :refer [match]]))

; ------------------
; Types declarations
; ------------------

(t/defalias Var
  '[(t/Value :var) String])

(t/defalias Num
  '[(t/Value :num) Number])

(t/defalias Sum
  '{:addend AlgExp :augend AlgExp})

(t/defalias Product
  '{:multiplier AlgExp :multiplicand AlgExp})

(t/defalias Exponent
  '{:base AlgExp :exponent Num})

(t/defalias AlgExp
  (t/U Var
       Num
       '[(t/Value :sum)  Sum]
       '[(t/Value :prod) Product]
       '[(t/Value :exp)  Exponent]))

(t/defalias Op
  (t/U (t/Value '*)
       (t/Value '+)
       (t/Value (symbol "^"))))

(t/defalias ExpS
  (t/U Number
       Op
       t/Symbol
       (t/List ExpS)))

; ----------------------
; Constructors & Getters
; ----------------------

(t/ann make-var [String -> Var])
(defn make-var [name]
  [:var name])

(t/ann get-addend [Sum -> AlgExp])
(defn get-addend [sum]
  (:addend sum))

(t/ann get-augend [Sum -> AlgExp])
(defn get-augend [sum]
  (:augend sum))

(t/ann get-multiplier [Product -> AlgExp])
(defn get-multiplier [prod]
  (:multiplier prod))

(t/ann get-multiplicand [Product -> AlgExp])
(defn get-multiplicand [prod]
  (:multiplicand prod))

(t/ann get-base [Exponent -> AlgExp])
(defn get-base [exp]
  (:base exp))

(t/ann get-exponent [Exponent -> Num])
(defn get-exponent [exp]
  (:exponent exp))

(t/ann is-zero? [AlgExp -> Boolean])
(defn is-zero? [exp]
  (match exp
         [:num 0]    true
         _           false))

(t/ann make-sum [AlgExp AlgExp -> AlgExp])
(defn make-sum
  [left right]
  (cond
   (is-zero? left)   right
   (is-zero? right)  left
   :otherwise        [:sum {:addend left :augend right}]))

(t/ann is-one? [AlgExp -> Boolean])
(defn is-one? [exp]
  (match exp
         [:num 1]    true
         _           false))

(t/ann make-num [Number -> Num])
(defn make-num [n]
  [:num n])

(t/ann make-prod [AlgExp AlgExp -> AlgExp])
(defn make-prod
  [left right]
  (cond
   (or (is-zero? left) (is-zero? right))   (make-num 0)
   (is-one? left)                          right
   (is-one? right)                         left
   :otherwise                              [:prod {:multiplier left :multiplicand right}]))

(t/ann make-exp [AlgExp Num -> AlgExp])
(defn make-exp
  [base exponent]
  (cond
   (is-zero? exponent)  (make-num 1)
   (is-one?  exponent)  base
   :otherwise           [:exp {:base base :exponent exponent}]))

(t/ann ^:no-check to-alg-exp [ExpS -> AlgExp])
(defn to-alg-exp [exp]
  "Converts a symbol expression (e.g. '(* (+ x 3) (exp y 2)) ) into a AlgExp value"
  (cond (number? exp)
          [:num exp]
        (list? exp)
          (let [[op left right] (to-array exp)]
            (match op
                   '+     (make-sum   (to-alg-exp left) (to-alg-exp right))
                   '*     (make-prod  (to-alg-exp left) (to-alg-exp right))
                   'exp   (make-exp   (to-alg-exp left) (to-alg-exp right))
                   ))
        (symbol? exp)
          (make-var (name exp))))

(t/ann var-name [Var -> String])
(defn var-name [[:var var-name]]
  var-name)

(t/ann decrease-number [Num -> Num])
(defn decrease-number [[:num n]]
  (make-num (dec n)))

(t/ann derive-alg-exp [AlgExp Var -> AlgExp])
(defn derive-alg-exp
  [alg-exp var]
  (match alg-exp
         [:num _]
           (make-num 0)
         [:var exp-var-name]
           (if (= (var-name var) exp-var-name)
             (make-num 1)
             (make-num 0))
         [:sum sum]
           (make-sum
            (derive-alg-exp (get-addend sum) var)
            (derive-alg-exp (get-augend sum) var))
         [:prod prod]
           (make-sum
            (make-prod
             (get-multiplier prod)
             (derive-alg-exp (get-multiplicand prod) var))
            (make-prod
             (derive-alg-exp (get-multiplier prod) var)
             (get-multiplicand prod)))
         [:exp exponentiation]
           (make-prod
            (get-exponent exponentiation)
            (make-prod
             (make-exp
               (get-base exponentiation)
               (decrease-number (get-exponent exponentiation)))
             (derive-alg-exp (get-base exponentiation) var)))))
