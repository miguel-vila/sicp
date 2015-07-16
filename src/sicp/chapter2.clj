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


