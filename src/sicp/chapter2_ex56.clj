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
  '{:summands (t/NonEmptySeq AlgExp)})

(t/defalias Product
  '{:multiplicands (t/NonEmptySeq AlgExp)})

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

; ------------
; Constructors
; ------------

(declare is-one? is-zero?)

(t/ann make-num [Number -> Num])
(defn make-num [n]
  [:num n])

(t/ann make-var [String -> Var])
(defn make-var [name]
  [:var name])

(t/ann make-sum [AlgExp * -> AlgExp])
(defn make-sum
  [& _exps]
  (let [exps (filter (complement is-zero?) (seq _exps))]
    (cond
     (empty? exps)         (make-num 0)
     (= (count exps) 1)    (first exps)
     :otherwise            [:sum {:summands exps}])))

(t/ann make-prod [AlgExp * -> AlgExp])
(defn make-prod
  [& _exps]
  (let [exps (filter (complement is-one?) (seq _exps))]
    (cond
     (empty? exps)         (make-num 1)
     (some is-zero? exps)  (make-num 0)
     (= (count exps) 1)    (first exps)
     :otherwise            [:prod {:multiplicands exps}])))

(t/ann make-exp [AlgExp Num -> AlgExp])
(defn make-exp
  [base exponent]
  (cond
   (is-zero? exponent)  (make-num 1)
   (is-one?  exponent)  base
   :otherwise           [:exp {:base base :exponent exponent}]))

; -------
; Getters
; -------

(t/ann get-addend [Sum -> AlgExp])
(defn get-addend [sum]
  (first (:summands sum)))

(t/ann get-augend [Sum -> AlgExp])
(defn get-augend [sum]
  (let [tail (rest (:summands sum))]
    (apply make-sum tail)))

(t/ann get-multiplier [Product -> AlgExp])
(defn get-multiplier [prod]
  (first (:multiplicands prod)))

(t/ann get-multiplicand [Product -> AlgExp])
(defn get-multiplicand [prod]
  (let [tail (rest (:multiplicands prod))]
    (apply make-prod tail)))

(t/ann get-base [Exponent -> AlgExp])
(defn get-base [exp]
  (:base exp))

(t/ann get-exponent [Exponent -> Num])
(defn get-exponent [exp]
  (:exponent exp))

(t/ann var-name [Var -> String])
(defn var-name [[:var var-name]]
  var-name)

; ----------
; Predicates
; ----------

(t/ann is-zero? [AlgExp -> Boolean])
(defn is-zero? [exp]
  (match exp
         [:num 0]    true
         _           false))

(t/ann is-one? [AlgExp -> Boolean])
(defn is-one? [exp]
  (match exp
         [:num 1]    true
         _           false))

; ------------------
; Helper constructor
; ------------------

(t/ann ^:no-check to-alg-exp [ExpS -> AlgExp])
(defn to-alg-exp [exp]
  "Converts a symbol expression (e.g. '(* (+ x 3) (exp y 2)) ) into a AlgExp value"
  (cond (number? exp)
          [:num exp]
        (list? exp)
          (let [[op & r] (to-array exp)
                [left right] r]
            (match op
                   '+     (apply make-sum  (map to-alg-exp r))
                   '*     (apply make-prod (map to-alg-exp r))
                   'exp   (make-exp   (to-alg-exp left) (to-alg-exp right))
                   ))
        (symbol? exp)
          (make-var (name exp))))

; ---------
; Modifiers
; ---------

(t/ann decrease-number [Num -> Num])
(defn decrease-number [[:num n]]
  (make-num (dec n)))

; ---------
; Derivator
; ---------

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
            (make-exp
             (get-base exponentiation)
             (decrease-number (get-exponent exponentiation)))
            (derive-alg-exp (get-base exponentiation) var))))
