(ns sicp.chapter2
  (:require [sicp.chapter1 :as chapter1]))

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

