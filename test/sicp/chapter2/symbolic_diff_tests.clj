(ns sicp.chapter2.symbolic_diff_tests
  (:require [sicp.chapter2.symbolic_diff :refer :all]
            [sicp.chapter2.symbolic_diff_parsing :refer :all])
  (:use midje.sweet))

(facts "to-alg-exp-prefix"
       (to-alg-exp-prefix '7)                         => (make-num 7)
       (to-alg-exp-prefix 'x)                         => (make-var "x")
       (to-alg-exp-prefix '(* 2 x))                   => (make-prod (make-num 2) (make-var "x"))
       (to-alg-exp-prefix '(+ (* 3 x) (* 2 y)))       => (make-sum (make-prod (make-num 3) (make-var "x")) (make-prod (make-num 2) (make-var "y")))
       (to-alg-exp-prefix '(* x y))                   => (make-prod (make-var "x") (make-var "y"))
       (to-alg-exp-prefix '(exp x 2))                 => (make-exp (make-var "x") (make-num 2))
       (to-alg-exp-prefix '(+ 2 x y))                 => (make-sum (make-num 2) (make-var "x") (make-var "y"))
       (to-alg-exp-prefix '(+ 0 2 x 0))               => (make-sum (make-num 2) (make-var "x"))
       (to-alg-exp-prefix '(* 0 2 x))                 => (make-num 0)
       (to-alg-exp-prefix '(* 1 2 x 1))               => (make-prod (make-num 2) (make-var "x"))
       (to-alg-exp-prefix '(exp x 1))                 => (make-var "x")
       (to-alg-exp-prefix '(exp (* x 3) 0))           => (make-num 1))

(facts "to-alg-exp-infix-1"
       (to-alg-exp-infix-1 '7)                         => (make-num 7)
       (to-alg-exp-infix-1 'x)                         => (make-var "x")
       (to-alg-exp-infix-1 '(2 * x))                   => (make-prod (make-num 2) (make-var "x"))
       (to-alg-exp-infix-1 '((3 * x) + (2 * y)))       => (make-sum (make-prod (make-num 3) (make-var "x")) (make-prod (make-num 2) (make-var "y")))
       (to-alg-exp-infix-1 '(x * y))                   => (make-prod (make-var "x") (make-var "y"))
       (to-alg-exp-infix-1 '(x exp 2))                 => (make-exp (make-var "x") (make-num 2))
       (to-alg-exp-infix-1 '(2 + (x + y)))             => (make-sum (make-num 2) (make-var "x") (make-var "y"))
       (to-alg-exp-infix-1 '(2 * (x * y)))             => (make-prod (make-num 2) (make-var "x") (make-var "y"))
       (to-alg-exp-infix-1 '(0 + (2 + (x + 0))))       => (make-sum (make-num 2) (make-var "x"))
       (to-alg-exp-infix-1 '(0 * (2 * x)))             => (make-num 0)
       (to-alg-exp-infix-1 '(1 * (2 * (x * 1))))       => (make-prod (make-num 2) (make-var "x"))
       (to-alg-exp-infix-1 '(x exp 1))                 => (make-var "x")
       (to-alg-exp-infix-1 '((x * 3) exp 0))           => (make-num 1))

(facts "to-alg-exp-infix-2"
       (to-alg-exp-infix-2 '7)                         => (make-num 7)
       (to-alg-exp-infix-2 'x)                         => (make-var "x")
       (to-alg-exp-infix-2 '(1 + 2 + 3 + 4))           => (make-sum (make-num 1) (make-num 2) (make-num 3) (make-num 4))
       (to-alg-exp-infix-2 '(2 * 3))                   => (make-prod (make-num 2) (make-num 3))
       (to-alg-exp-infix-2 '(1 + 2 * 3))               => (make-sum (make-num 1) (make-prod (make-num 2) (make-num 3)))
       (to-alg-exp-infix-2 '(2 * 3 + 4))               => (make-sum (make-prod (make-num 2) (make-num 3)) (make-num 4))
       (to-alg-exp-infix-2 '(1 + 2 * 3 + 4))           => (make-sum (make-num 1) (make-prod (make-num 2) (make-num 3)) (make-num 4))
       (to-alg-exp-infix-2 '(2 * 3 + 1))               => (make-sum (make-prod (make-num 2) (make-num 3)) (make-num 1))
       (to-alg-exp-infix-2 '((2 * 3) + 1))             => (make-sum (make-prod (make-num 2) (make-num 3)) (make-num 1))
       (to-alg-exp-infix-2 '(x exp 1))                 => (make-var "x")
       (to-alg-exp-infix-2 '(x exp 2))                 => (make-exp (make-var "x") (make-num 2))
       (to-alg-exp-infix-2 '(x exp 2 + 3))             => (make-sum (make-exp (make-var "x") (make-num 2)) (make-num 3))
       (to-alg-exp-infix-2 '(x * 3 exp 3))             => (make-exp (make-prod (make-var "x") (make-num 3)) (make-num 3))
;       (to-alg-exp-infix-2 '(x * 3 exp 3 + 2))         => (make-sum (make-exp (make-prod (make-var "x") (make-num 3)) (make-num 3)) (make-num 2))
; @TODO ^
       )

(facts "deriv"
       (deriv
        (make-num 7)
        (make-var "x")) =>

       (make-num 0)


       (deriv
        (make-var "x")
        (make-var "x")) =>

       (make-num 1)


       (deriv
        (make-var "x")
        (make-var "y")) =>

       (make-num 0)


       (deriv
        (make-prod
          (make-num 2)
          (make-var "x"))
        (make-var "x")) =>

       (make-num 2)


       (deriv
        (make-sum
          (make-prod
            (make-num 3)
            (make-var "x"))
          (make-num 7))
        (make-var "x")) =>

       (make-num 3)


       (deriv
        (make-sum
          (make-prod
            (make-num 3)
            (make-var "x"))
          (make-prod
           (make-num 2)
           (make-var "y")))
       (make-var "x")) =>

       (make-num 3)


       (deriv
        (make-prod
          (make-var "x")
          (make-var "y"))
       (make-var "x")) =>

       (make-var "y")


       (deriv
        (make-prod
          (make-prod
            (make-var "x")
            (make-var "y"))
          (make-sum
            (make-var "x")
            (make-num 3)))
       (make-var "x")) =>

       (make-sum
        (make-prod
         (make-var "x")
         (make-var "y"))
        (make-prod
         (make-var "y")
         (make-sum
          (make-var "x")
          (make-num 3))))


       (deriv
        (make-prod
          (make-var "x")
          (make-var "y")
          (make-sum
            (make-var "x")
            (make-num 3)))
       (make-var "x")) =>

       (make-sum
        (make-prod
         (make-var "x")
         (make-var "y"))
        (make-prod
         (make-var "y")
         (make-sum
          (make-var "x")
          (make-num 3))))


       (deriv
        (make-exp
          (make-var "x")
          (make-num 2))
       (make-var "x")) =>

       (make-prod
        (make-num 2)
        (make-var "x"))


       (deriv
        (make-sum
          (make-prod
            (make-num 3)
            (make-var "x"))
          (make-prod
            (make-num 2)
            (make-var "y"))
          (make-exp
            (make-var "x")
            (make-num 2)))
        (make-var "x")) =>

       (make-sum
        (make-num 3)
        (make-prod
         (make-num 2)
         (make-var "x"))))
