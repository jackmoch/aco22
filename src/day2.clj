(ns day2
  (:require [clojure.string :as str]))

(def sample "A Y\nB X\nC Z")
(def day2-input (slurp "./src/day2-input.txt"))
(def mapper
  {\A {\X 4 \Y 8 \Z 3}
   \B {\X 1 \Y 5 \Z 9}
   \C {\X 7 \Y 2 \Z 6}})

(def rotated-mapper
  (let [rotate-n #(->> (vals %2) cycle (drop %1) (zipmap (keys %2)))]
    (-> mapper
        (update \A (partial rotate-n 2))
        (update \C (partial rotate-n 1)))))

(defn determine-total-score
  [input-data part]
  (transduce
    (comp (map (juxt first last))
          (map (partial get-in (if (= 1 part) mapper rotated-mapper))))
    +
    (str/split-lines input-data)))

(defn part1 [] (determine-total-score day2-input 1))
(defn part2 [] (determine-total-score day2-input 2))

(comment
  (part1)
  (part2))
