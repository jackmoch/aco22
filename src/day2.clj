(ns day2
  (:require [clojure.string :as str]))

(def sample "A Y\nB X\nC Z")
(def day2-input (slurp "./src/day2-input.txt"))
(def char->value-mapping {\A 1 \B 2 \C 3 \X 1 \Y 2 \Z 3})
(def mapper
  {1 {1 {1 4 2 8 3 3}
      2 {1 1 2 5 3 9}
      3 {1 7 2 2 3 6}}
   2 {1 {1 3 2 4 3 8}
      2 {1 1 2 5 3 9}
      3 {1 2 2 6 3 7}}})

(defn determine-total-score
  [input-data part]
  (transduce
    (comp (map (partial replace char->value-mapping))
          (map (juxt first last))
          (map (partial cons part))
          (map (partial get-in mapper)))
    +
    (str/split-lines input-data)))

(defn part1 [] (determine-total-score day2-input 1))
(defn part2 [] (determine-total-score day2-input 2))

(comment
  (part1)
  (part2))
