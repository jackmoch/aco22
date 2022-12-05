(ns day4
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def sample "2-4,6-8\n2-3,4-5\n5-7,7-9\n2-8,3-7\n6-6,4-6\n2-6,4-8")
(def day4-input (slurp "./src/day4-input.txt"))

(defn str->ints [s]
  (into [] (comp (map #(str/split % #"-"))
                 (map (partial map #(Integer/parseInt %)))
                 (map (juxt first (comp inc second)))
                 (map (partial apply range))
                 (map set))
        (str/split s #",")))

(defn find-overlaps [set-logic]
  (transduce
    (comp (map #(str/split % #","))
          (map (partial mapcat str->ints))
          (map (partial sort-by count))
          set-logic)
    (fn count-total
      ([] [])
      ([result] (count result))
      ([result input] (conj result input)))
    (-> day4-input str/split-lines)))

(defn part1 [] (find-overlaps (comp (map (partial apply set/difference))
                                    (filter empty?))))

(defn part2 [] (find-overlaps (comp (map (partial apply set/intersection))
                                    (filter not-empty))))

(comment
  (part1)
  (part2))
