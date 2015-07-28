(ns sicp.chapter2
  (:require [sicp.chapter1 :as chapter1]
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
  (fn [f] (fn [x] (f (f x)))))

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

