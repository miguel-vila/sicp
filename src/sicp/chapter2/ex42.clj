(ns sicp.chapter2.ex42
  (:require ;[clojure.core.typed :as t]
   [sicp.chapter2.exercises :refer [flatmap]]
   [clojure.core.match :refer [match]]))

;(t/defalias Position
;  '[Integer lInteger])

;(t/ann row [Position -> Integer])
(defn row [p]
  (first p))

;(t/ann col [Position -> Integer])
(defn col [p]
  (second p))

;(t/defalias BoardPositions
;  (t/NonEmptySeq Position))

;(t/ann adjoin-position [Position BoardPositions -> BoardPositions])
(defn adjoin-position [r c positions]
  (cons (list r c) positions))

;(t/ann enumerate-interval [Integer Integer -> (t/Seq t/AnyInteger)])
(defn enumerate-interval [x y]
  (range x (inc y)))

;(t/ann empty-board BoardPositions)
(def empty-board '())

;(t/ann exists-with-same-row? [Integer BoardPositions -> t/Bool])
(defn exists-with-same-row? [r positions]
  (not (nil? (some #(= (row %) r) positions))))

(defn in-same-diagonal? [p1 p2]
  (let [r1 (row p1)
        c1 (col p1)
        r2 (row p2)
        c2 (col p2)]
    (= (Math/abs (- r1 r2))
       (Math/abs (- c1 c2)))))

;(t/ann exists-in-same-diagonal? [Position BoardPositions -> t/Bool])
(defn exists-in-same-diagonal? [p positions]
  (not (nil? (some #(in-same-diagonal? % p) positions))))

;(t/ann safe? [Integer BoardPositions -> t/Bool])
(defn safe? [k board]
  (let [queen-pos (first board)
        qrow (row queen-pos)
        qcol (col queen-pos)
        others (rest board)]
    (and
     (not (exists-with-same-row? qrow others))
     (not (exists-in-same-diagonal? queen-pos others)))))

(exists-in-same-diagonal? '(1 1) '((2 2)))
(safe? 2 '((1 1)))

(defn queens [board-size]
  (defn queen-cols [k]
    (if (= k 0)
      (list empty-board)
      (filter
       #(safe? k %)
       (flatmap
        (fn [rest-of-queens]
          (map #(adjoin-position % k rest-of-queens)
               (enumerate-interval 1 board-size)))
        (queen-cols (dec k))))))
  (queen-cols board-size))

