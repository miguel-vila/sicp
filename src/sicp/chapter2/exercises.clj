(ns sicp.chapter2.exercises
  (:require [sicp.chapter1.exercises :as chapter1]
            [clojure.core.match :refer [match]]))

(defn gcd [a b]
  (if (= (mod a b) 0)
    b
    (gcd b (rem a b))))

;; Ex 2.1
(defn make-rat [n d]
  (let [g (gcd n d)
        numer (quot n g)
        denom (quot d g)]
    (if (or
         (and (pos? numer) (pos? denom))
         (and (neg? numer) (neg? denom)))
      [(Math/abs numer) (Math/abs denom)]
      [(- (Math/abs numer)) (Math/abs denom)])))

(defn numer [rat]
  (first rat))

(defn denom [rat]
  (second rat))

;; Ex 2.2
(defrecord Point [x y])

(defn make-point [x y]
  (Point. x y))

(defn x-point [p]
  (:x p))

(defn y-point [p]
  (:y p))

(defrecord Segment [sp ep])

(defn make-segment [sp ep]
  (Segment. sp ep))

(defn start-segment [s]
  (:sp s))

(defn end-segment [s]
  (:ep s))

(defn midpoint-segment [s]
  (let [sp (start-segment s)
        ep (end-segment s)
        x (chapter1/average (x-point sp) (x-point ep))
        y (chapter1/average (y-point sp) (y-point ep))]
    (make-point x y)))

(defn manhattan-distance [p1 p2]
  (let [w (Math/abs (- (x-point p1) (x-point p2)))
        h (Math/abs (- (x-point p1) (x-point p2)))]
    (+ w h)))

(defrecord Rectangle [p1 p2])

(defn make-rectangle [p1 p2]
  (Rectangle. p1 p2))

(defn p1-rectangle [r]
  (:p1 r))

(defn p2-rectangle [r]
  (:p2 r))

(defn height-rectangle [r]
  (let [p1 (p1-rectangle r)
        p2 (p2-rectangle r)]
    (Math/abs (- (x-point p1) (x-point p2)))))

(defn width-rectangle [r]
  (let [p1 (p1-rectangle r)
        p2 (p2-rectangle r)]
    (Math/abs (- (y-point p1) (y-point p2)))))

(defn perimeter-rectangle [r]
  (* 2 (+ (width-rectangle r) (height-rectangle r))))

(defn area-rectangle [r]
  (* (width-rectangle r) (height-rectangle r)))

(defn zero [f]
  (fn [x] x))

(defn add-1 [n]
  (fn [f] (fn [x] (f ((n f) x)))))

(def one
  (fn [f] (fn [x] (f x))))

(def two
  (fn [f] (comp f f)))

(def three
  (fn [f] (comp f f f)))

(defn plus [a b]
  (fn [f]
    (let
      [f1 (a f)
       f2 (b f)]
      (fn [x] (f1 (f2 x))))))

;;Ex 2.17
(defn last-pair [xs]
  (cond (empty? xs) nil
        (empty? (rest xs)) xs
        :else (last-pair (rest xs))))

;;Ex 2.18
(defn reverse-list [xs]
  (defn go [xs acc]
    (if (empty? xs)
      acc
      (go (rest xs) (cons (first xs) acc))))
  (go xs (list)))

;;Ex 2.20
(defn same-parity [x & rest]
  (let [parity (mod x 2)]
    (cons x (filter (fn [y] (= (mod y 2) parity)) rest))))

;;Ex 2.23
(defn for-each [f xs]
  (when (not (empty? xs))
    (f (first xs))
    (for-each f (rest xs))))

;;Ex 2.27
(defn deep-reverse [xs]
  (reverse-list (map reverse-list xs)))

;;Ex 2.28
(defn fringe [xs]
  (defn fringe-item [xs]
    (cond (nil? xs) xs
          (list? xs)
          (fringe xs)
          :else [xs]))
  (if (empty? xs)
    xs
    (let
      [as (first xs)
       bs (first (rest xs))]
      (concat (fringe-item as) (fringe-item bs)))))

;;Ex 2.32
(defn subsets [s]
  (if (or (= s '()) (nil? s))
    (list '())
    (let [rest-subsets (subsets (rest s))]
      (concat rest-subsets (map (fn [subset] (cons (first s) subset)) rest-subsets)))))

(defn accumulate [f z xs]
  (if (or (= xs '()) (nil? xs))
    z
    (f (first xs)
       (accumulate f z (rest xs)))))

;;Ex 2.33
(defn map-using-accumulate [f xs]
  (accumulate #(cons (f %1) %2) nil xs))

(defn append-using-accumulate [xs ys]
  (accumulate cons ys xs))

(defn length-using-accumulate [xs]
  (accumulate #(inc %2) 0 xs))

;;Ex 2.34
(defn horner-eval [x cffs]
  (accumulate (fn [cff px] (+ cff (* x px))) 0 cffs))

;;Ex 2.36
(defn accumulate-n [f z xss]
  (if (or (= (first xss) '()) (nil? (first xss)))
    nil
    (cons (accumulate f z (map first xss))
          (accumulate-n f z (map rest xss)))))

;;(accumulate-n + 0 '((1 2 3) (4 5 6) (7 8 9) (10 11 12)))

;;Ex 2.37
(defn dot-product [v w]
  (accumulate + 0 (map * v w)))

;;(dot-product (list 1 2 3) (list 4 5 6))

(defn matrix-*-vector [m v]
  (map #(dot-product % v) m))

(def m (list
        (list 1 2 3 4)
        (list 4 5 6 6)
        (list 6 7 8 9)))

(dot-product (list 1 2 3 4) (list 1 2 3 4))
(dot-product (list 1 2 3 4) (list 4 5 6 6))
(dot-product (list 1 2 3 4) (list 6 7 8 9))
(matrix-*-vector m (list 1 2 3 4))

;;Ex 2.39
(defn fold-right [f z xs]
  (if (or (= xs '()) (nil? xs))
    z
    (f (first xs)
       (fold-right f z (rest xs)))))

(defn fold-left [f z xs]
  (defn iter [acc xs]
    (if (or (= xs '()) (nil? xs))
      acc
      (iter (f acc (first xs)) (rest xs))))
  (iter z xs))

(defn my-reverse-fold-right [xs]
  (fold-right (fn[x y] (concat y (list x))) nil xs))

(my-reverse-fold-right (list 1 2 3))

(defn my-reverse-fold-left [xs]
  (fold-left (fn [x y] (cons y x)) nil xs))

(my-reverse-fold-left (list 1 2 3))

(defn flatmap [f xs]
  (accumulate concat nil (map f xs)))

(flatmap (fn [i] (range 0 (inc i))) (range 1 5))

;;Ex 2.40
(defn unique-pairs [n]
  (flatmap
   (fn [j] (map (fn [i] (list i j))
                (range 1 j)))
   (range 1 (inc n))))

(unique-pairs 3)

(defn unique-triples [n]
  (flatmap
   (fn [pair]
     (let [[i j] pair]
       (map
        (fn [k] (list i j k))
        (range (inc j) (inc n)))))
   (unique-pairs n)))

(unique-triples 4)

;;Ex 2.41
(defn sum [xs]
  (accumulate + 0 xs))

(defn find-triples-sum [n s]
  (filter
   #(= (sum %) s)
   (unique-triples n)))

; Ex 2.54
(defn eq-symbol [xs ys]
  (cond
   (and (symbol? xs) (symbol? ys)) (= xs ys)
   (and (list? xs) (list? ys))
     (or (and (empty? xs) (empty? ys))
         (and (= (first xs) (first ys)) (eq-symbol (rest xs) (rest ys))))
   :otherwise false))
