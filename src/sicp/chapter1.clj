(ns sicp.chapter1)

(defn square [a]
  (* a a))

(defn sum-squares [a b]
  (+ (square a) (square b)))

(defn biggest-two [a b c]
  (if (> a b)
    (if (> b c)
      [a b]
      [a c])
    (if (> c a)
      [c b]
      [a b])))

;;Ex 1.3
(defn square-sum-biggest [a b c]
  (let [[m n] (biggest-two a b c)]
    (sum-squares m n)))

;;Ex 1.11
(defn f-rec [n]
  (cond
   (< n 3) n
   :else (+
          (f-rec (- n 1))
          (* 2 (f-rec (- n 2)))
          (* 3 (f-rec (- n 3))))))

(defn f-iter [n]
  (defn iter [n a b c]
    (cond
     (= n 0) a
     (= n 1) b
     (= n 2) c
     :else (iter (dec n) b c (+ c (* 2 b) (* 3 a)))))
  (iter n 0 1 2))

;;Ex 1.12
(defn pascal [n m]
  (if (or (= m 0) (= n m))
    1
    (+ (pascal (- n 1) m) (pascal (- n 1) (- m 1)))))

;;Ex 1.16
(defn fast-expn-iter [b n]
  (defn iter [exp a]
    (cond
     (= exp n) a
     (or
      (> (* 2 exp) n)
      (= exp 0)) (iter (inc exp) (* b a))
     :else (iter (* 2 exp) (* a a))))
  (iter 0 1))

;;Ex 1.17
(defn *2 [x]
  (+ x x))

(defn fast-mult-rec [a b]
  (cond
   (= b 0) 0
   (= (mod b 2) 0) (*2 (fast-mult-rec a (/ b 2)))
   :else (+ a (fast-mult-rec a (dec b)))))

;;Ex 1.18
(defn fast-mult-iter [a b]
  (defn iter [mult res]
    (cond
     (= mult b) res
     (or
      (> (* 2 mult) b)
      (= mult 0)) (iter (inc mult) (+ res a))
     :else (iter (* 2 mult) (*2 res))))
  (iter 0 0))
