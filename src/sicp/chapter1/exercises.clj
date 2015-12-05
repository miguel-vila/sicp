(ns sicp.chapter1.exercises)

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

;;Ex 1.28
(defn square-mod-n [x n]
  (mod (square x) n))

(defn non-trivial-square-root-of-1-mod-n [x n]
  (and
   (not= x 1)
   (not= x (- n 1))
   (= (square-mod-n x n) 1)))

(defn expmod [base exp m]
  (cond
   (= exp 0) 1
   (even? exp) (rem (square (expmod base (/ exp 2) m)) m)
   :else (rem (* base (expmod base (- exp 1) m)) m )))

;;Ex 1.37
(defn cont-frac [n d k]
  (defn loop [i]
    (let
      [_n (n i)
       _d (d i)]
      (if (= i k)
        (/ _n _d)
        (/ _n (+ _d (loop (inc i)))))))
  (loop 1))

(defn cont-frac-iter [n d k]
  (defn loop [i acc]
    (if (= i 0)
      acc
      (loop (dec i) (/ (n i) (+ (d i) acc)))))
  (loop k 0.0))

(defn approx-phi [k]
  (/ 1 (cont-frac (fn [_] 1.0) (fn [_] 1.0) k)))

(defn approx-phi-iter [k]
  (/ 1 (cont-frac-iter (fn [_] 1.0) (fn [_] 1.0) k)))

(approx-phi 2)

(approx-phi-iter 2)

;; Ex 1.38
(defn approx-e [k]
  (defn _seq [i]
    (cond
     (= i 1) 1
     (= (mod (- i 2) 3) 0) (* 2 (inc (/ (- i 2) 3)))
     :else 1))
  (+ 2 (cont-frac-iter (fn [_] 1.0) _seq k)))

(approx-e 10)

(defn tan-cf [x k]
  (- (cont-frac-iter (fn [i] (- (Math/pow x i))) (fn [i] (inc (* (dec i) 2))) k)))

(Math/tan 1)
(tan-cf 1 3)

;; Ex. 1.41
(defn _double [f]
  (fn [x] (f (f x))))

;; Ex. 1.42
(defn compose [f g]
  (fn [x] (f (g x))))

;; Ex. 1.43
(defn repeated [f n]
  (if (= n 1)
    f
    (compose f (repeated f (dec n)))))

(defn repeated [f n]
  (reduce compose (take n (repeat f))))

((repeated square 1) 5)

;; Ex. 1.44
(defn average [& all]
  (/ (reduce + all) (count all)))

(defn smoothed [f dx]
  (fn [x] (average
           (f (- x dx))
           (f x)
           (f (+ x dx)))))
