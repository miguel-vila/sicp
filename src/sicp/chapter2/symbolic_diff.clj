(ns sicp.chapter2.symbolic_diff
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

; ------------
; Constructors
; ------------

(declare is-one? is-zero? get-all-summands get-all-multiplicands)

(t/ann make-num [Number -> Num])
(defn make-num [n]
  [:num n])

(t/ann make-var [String -> Var])
(defn make-var [name]
  [:var name])

(t/ann ^:no-check make-sum [AlgExp * -> AlgExp]) ;core typed no infiere correctamente el tipo del último caso :(
(defn make-sum
  [& _exps]
  (let [exps (filter (complement is-zero?) (seq _exps))]
    (cond
     (empty? exps)         (make-num 0)
     (= (count exps) 1)    (first exps)
     :otherwise            (t/letfn> [extract-summands :- [AlgExp -> (t/NonEmptySeq AlgExp)]
                                      (extract-summands [_exp]
                                                        (match _exp
                                                               [:sum sum] (get-all-summands sum)
                                                               _          (seq [_exp])))]
                                     [:sum {:summands (mapcat extract-summands exps)}])))) ; Debería inferir (NonEmptySeq AlgExp) pero infiere (Seq AlgExp)

; no chequeada por lo mismo que la anterior
(t/ann ^:no-check make-prod [AlgExp * -> AlgExp])
(defn make-prod
  [& _exps]
  (let [exps (filter (complement is-one?) (seq _exps))]
    (cond
     (empty? exps)         (make-num 1)
     (some is-zero? exps)  (make-num 0)
     (= (count exps) 1)    (first exps)
     :otherwise            (t/letfn> [extract-multiplicands :- [AlgExp -> (t/NonEmptySeq AlgExp)]
                                      (extract-multiplicands [_exp]
                                                             (match _exp
                                                                    [:prod prod] (get-all-multiplicands prod)
                                                                    _            (seq [_exp])))]
                                     [:prod {:multiplicands (mapcat extract-multiplicands exps)}]))))

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

(t/ann get-all-summands [Sum -> (t/NonEmptySeq AlgExp)])
(defn get-all-summands
  [sum]
  (:summands sum))

(t/ann get-addend [Sum -> AlgExp])
(defn get-addend [sum]
  (first (:summands sum)))

(t/ann get-augend [Sum -> AlgExp])
(defn get-augend [sum]
  (let [tail (rest (:summands sum))]
    (apply make-sum tail)))

(t/ann get-all-multiplicands [Product -> (t/NonEmptySeq AlgExp)])
(defn get-all-multiplicands
  [prod]
  (:multiplicands prod))

(t/ann get-multiplier [Product -> AlgExp])
(defn get-multiplier [prod]
  (first (get-all-multiplicands prod)))

(t/ann get-multiplicand [Product -> AlgExp])
(defn get-multiplicand [prod]
  (let [tail (rest (get-all-multiplicands prod))]
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

; ---------
; Modifiers
; ---------

(t/ann decrease-number [Num -> Num])
(defn decrease-number [[:num n]]
  (make-num (dec n)))

; ---------
; Derivator
; ---------

(t/ann deriv [AlgExp Var -> AlgExp])
(defn deriv
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
            (deriv (get-addend sum) var)
            (deriv (get-augend sum) var))
         [:prod prod]
           (make-sum
            (make-prod
             (get-multiplier prod)
             (deriv (get-multiplicand prod) var))
            (make-prod
             (deriv (get-multiplier prod) var)
             (get-multiplicand prod)))
         [:exp exponentiation]
           (make-prod
            (get-exponent exponentiation)
            (make-exp
             (get-base exponentiation)
             (decrease-number (get-exponent exponentiation)))
            (deriv (get-base exponentiation) var))))
