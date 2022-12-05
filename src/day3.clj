(ns day3
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def sample
  "vJrwpWtwJgWrhcsFMMfFFhFp\njqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\nPmmdzqPrVvPwwTWBwg\nwMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\nttgJtRGJQctTZtZT\nCrZsJsPPZsGzwwsLwLmpwMDw")
(def day3-input (slurp "./src/day3-input.txt"))
(def priority-mapper (zipmap (map char (concat (range 97 123) (range 65 91)))
                             (drop 1 (range))))

(defn calcuate [partitioner input]
  (transduce
    (comp partitioner
          (mapcat (partial apply set/intersection))
          (map priority-mapper))
    +
    input))

(defn part1 [] (calcuate (map #(map set (partition (/ (count %) 2) %)))
                         (str/split-lines day3-input)))

(defn part2 [] (calcuate (map (partial map set))
                         (partition 3 (str/split-lines day3-input))))

(comment
  (part1)
  (part2))