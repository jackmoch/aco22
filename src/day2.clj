(ns day2
  (:require [clojure.string :as str]))

(def sample "A Y\nB X\nC Z")
(def day2-input (slurp "./src/day2-input.txt"))
(def mapper
  {1 {\A {\X 4 \Y 8 \Z 3}
      \B {\X 1 \Y 5 \Z 9}
      \C {\X 7 \Y 2 \Z 6}}
   2 {\A {\X 3 \Y 4 \Z 8}
      \B {\X 1 \Y 5 \Z 9}
      \C {\X 2 \Y 6 \Z 7}}})

(defn determine-total-score
  [input-data part]
  (transduce
    (comp (map (juxt first last))
          (map (partial cons part))
          (map (partial get-in mapper)))
    +
    (str/split-lines input-data)))

(defn part1 [] (determine-total-score day2-input 1))
(defn part2 [] (determine-total-score day2-input 2))

(comment
  (part1)
  (part2))
