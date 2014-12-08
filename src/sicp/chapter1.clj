(ns sicp.chapter1)

(defn square [a]
  (* a a))

(defn sum-squares [a b]
  (+ (square a) (square b)))

(defn square-sum-biggest [a b c]
  (if (> a b)
    (if (> b c)
      (sum-squares a b)
      (sum-squares a c))
    (if (> c a)
      (sum-squares c b)
      (sum-squares a b))))
