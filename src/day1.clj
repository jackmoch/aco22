(ns day1
  (:require [clojure.string :as str]))

(def sample "1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000")

(defn data-transform
  [reducing-function]
  (transduce
    (comp (map str/split-lines)
          (map (partial map #(Integer/parseInt %)))
          (map (partial apply +)))
    reducing-function
    (str/split (slurp "./src/day1-input.txt") #"\n{2,}")))

(defn day1 []
  (data-transform (completing (partial max 0))))

(defn day2 []
  (data-transform (fn aggregate-sum-and-sort
                    ([] [])
                    ([result] (->> result sort (take-last 3) (apply +)))
                    ([input result] (conj input result)))))
(comment
  (day1)
  (day2))