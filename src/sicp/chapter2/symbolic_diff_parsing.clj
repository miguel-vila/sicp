(ns sicp.chapter2.symbolic_diff_parsing
  (:require [clojure.core.match :refer [match]]
            [sicp.chapter2.symbolic_diff :refer :all]))

; ------------------
; Helper constructor
; ------------------

(defn to-alg-exp-prefix [exp]
  "Converts a symbol expression (e.g. '(* (+ x 3) (exp y 2)) ) into a AlgExp value"
  (cond (number? exp)
          [:num exp]
        (list? exp)
          (let [[op & r] (to-array exp)
                [left right] r]
            (match op
                   '+     (apply make-sum  (map to-alg-exp-prefix r))
                   '*     (apply make-prod (map to-alg-exp-prefix r))
                   'exp   (make-exp   (to-alg-exp-prefix left) (to-alg-exp-prefix right))
                   ))
        (symbol? exp)
          (make-var (name exp))))

; ---------
; Ex 2.58 a
; ---------

(defn to-alg-exp-infix-1 [exp]
  (cond
   (number? exp) (make-num exp)
   (symbol? exp) (make-var (name exp))
   (list? exp)
     (let [[n1 op n2] exp]
       (match op
              '+   (make-sum  (to-alg-exp-infix-1 n1) (to-alg-exp-infix-1 n2))
              '*   (make-prod (to-alg-exp-infix-1 n1) (to-alg-exp-infix-1 n2))
              'exp (make-exp  (to-alg-exp-infix-1 n1) (to-alg-exp-infix-1 n2))))))

; ---------
; Ex 2.58 b
; ---------

(defn parenthesize
  "Si la operación es * o exp pone paréntesis hacia la izquierda
   Sirve para asociar expresiones con mayor precedencia"
  [exp]
  (let [[n1 op & n2s] exp]
    (cond
     (= op '+)                  exp
     (or (= op '*) (= op 'exp)) (concat (list (list n1 op (first n2s))) (rest n2s)))))

(defn to-alg-exp-infix-2 [exp]
  (cond
   (number? exp) (make-num exp)
   (symbol? exp) (make-var (name exp))
   (and (list? exp) (= (count exp) 1)) (to-alg-exp-infix-2 (first exp))
   (and (list? exp) (> (count exp) 3))
     (let [[n1 op & n2] (parenthesize exp)]
       (match op
              '+   (make-sum  (to-alg-exp-infix-2 n1) (to-alg-exp-infix-2 n2))
              '*   (make-prod (to-alg-exp-infix-2 n1) (to-alg-exp-infix-2 n2))
              'exp (make-exp  (to-alg-exp-infix-2 n1) (to-alg-exp-infix-2 n2))))
   (and (list? exp) (= (count exp) 3))
     (let [[n1 op n2] exp]
       (match op
              '+   (make-sum  (to-alg-exp-infix-2 n1) (to-alg-exp-infix-2 n2))
              '*   (make-prod (to-alg-exp-infix-2 n1) (to-alg-exp-infix-2 n2))
              'exp (make-exp  (to-alg-exp-infix-2 n1) (to-alg-exp-infix-2 n2))))))
