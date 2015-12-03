(ns sicp.chapter2-test
  (:require [sicp.chapter2 :refer :all]
            [sicp.chapter2_ex29 :refer :all]
            [sicp.chapter2_ex30 :refer :all]
            [sicp.chapter2_ex42 :refer :all]
            [sicp.chapter2_symbolic_diff :refer :all])
  (:use midje.sweet))

(def mobile1 (make-mobile
              (make-branch 3 [:weight 4])
              (make-branch 5 [:weight 2])))

(def mobile2 (make-mobile
              (make-branch 3 [:weight 4])
              (make-branch 5 [:mobile (make-mobile
                                       (make-branch 2 [:weight 3])
                                       (make-branch 4 [:weight 2]))])))

(def mobile3 (make-mobile
              (make-branch 3 [:weight 4])
              (make-branch 3 [:weight 4])))

(def mobile4 (make-mobile
              (make-branch 3 [:weight 4])
              (make-branch 2 [:mobile (make-mobile
                                       (make-branch 7 [:weight 3])
                                       (make-branch 7 [:weight 3]))])))

(facts "total-weight"
       (total-weight mobile1) => 6
       (total-weight mobile2) => 9
       (total-weight mobile3) => 8
       (total-weight mobile4) => 10)

(facts "balanced?"
       (balanced? mobile1) => false
       (balanced? mobile2) => false
       (balanced? mobile3) => true
       (balanced? mobile4) => true)

(def tree1 (make-node
            (make-node
             (make-leaf 4)
             (make-leaf 3))
            (make-node
             (make-node
              (make-leaf 7)
              (make-leaf 2))
             (make-leaf (- 3)))))

(def squared1 (make-node
            (make-node
             (make-leaf 16)
             (make-leaf 9))
            (make-node
             (make-node
              (make-leaf 49)
              (make-leaf 4))
             (make-leaf 9))))

(facts "square-tree"
       (square-tree tree1) => squared1)

(facts "square-tree-using-map"
       (square-tree-using-map tree1) => squared1)

(facts "subsets"
       (subsets nil) => '(())
       (subsets (list 1)) => '(() (1))
       (subsets (list 1 2)) => '(() (2) (1) (1 2))
       (subsets (list 1 2 3)) => '(() (3) (2) (2 3) (1) (1 3) (1 2) (1 2 3)))

(facts "map-using-accumulate"
       (map-using-accumulate (fn [x] (* 2 x)) (list 1 2 3 4)) => '(2 4 6 8))

(facts "append-using-accumulate"
       (append-using-accumulate (list 1 2 3) (list 4 5 6 7)) => '(1 2 3 4 5 6 7))

(facts "length-using-accumulate"
       (length-using-accumulate '()) => 0
       (length-using-accumulate (list 1 2 3)) => 3)

(facts "horner-eval"
       (horner-eval 2 (list 1 3 0 5 0 1)) => 79)

(facts "queens"
       (queens 0) => '(())
       (queens 1) => '(((1 1)))
       (queens 2) => '()
       (queens 3) => '()
       (queens 4) => '(((3 4) (1 3) (4 2) (2 1)) ((2 4) (4 3) (1 2) (3 1))))

(facts "eq-symbol"
       (eq-symbol 'a 'a) => true
       (eq-symbol 'a 'b) => false
       (eq-symbol '() '()) => true
       (eq-symbol '(a) 'a) => false
       (eq-symbol '(list a b) '(list a b)) => true
       (eq-symbol '(list a b) '(list a c)) => false)

(def f #(+ 1 %))

(facts "church numerals"
       ((zero f) 0) => 0
       ((one f) 0) => 1
       ((two f) 0) = 2
       (((plus one zero) f) 0) => 1
       (((plus one two) f) 0) => 3
       (((plus one one) f) 0) => 2
       (((plus two three) f) 0) => 5)

(facts "derive-alg-exp"
       (derive-alg-exp (to-alg-exp '7) (make-var "x"))                   => (to-alg-exp '0)
       (derive-alg-exp (to-alg-exp 'x) (make-var "x"))                   => (to-alg-exp '1)
       (derive-alg-exp (to-alg-exp 'x) (make-var "y"))                   => (to-alg-exp '0)
       (derive-alg-exp (to-alg-exp '(* 2 x)) (make-var "x"))             => (to-alg-exp '2)
       (derive-alg-exp (to-alg-exp '(+ (* 3 x) 7)) (make-var "x"))       => (to-alg-exp '3)
       (derive-alg-exp (to-alg-exp '(+ (* 3 x) (* 2 y))) (make-var "x")) => (to-alg-exp '3)
       (derive-alg-exp (to-alg-exp '(* x y)) (make-var "x"))             => (to-alg-exp 'y)
       (derive-alg-exp (to-alg-exp '(* (* x y) (+ x 3))) (make-var "x")) => (to-alg-exp '(+ (* x y) (* y (+ x 3))))
       (derive-alg-exp (to-alg-exp '(* x y (+ x 3))) (make-var "x"))     => (to-alg-exp '(+ (* x y) (* y (+ x 3))))
       (derive-alg-exp (to-alg-exp '(exp x 2)) (make-var "x"))           => (to-alg-exp '(* 2 x))
       (derive-alg-exp (to-alg-exp '(+ (* 3 x) (* 2 y) (exp x 2))) (make-var "x")) => (to-alg-exp '(+ 3 (* 2 x)))
       )


