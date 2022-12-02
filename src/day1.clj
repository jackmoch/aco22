(ns day1
  (:require [clojure.string :as str]))

(def sample "1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000")
(def day1-input (slurp "./src/day1-input.txt"))

(defn data-transform
  [input-data reducing-function]
  (transduce
    (comp (map str/split-lines)
          (map (partial map #(Integer/parseInt %)))
          (map (partial apply +)))
    reducing-function
    (str/split input-data #"\n{2,}")))

(defn part1
  ([] (part1 false))
  ([print?] (part1 print? day1-input))
  ([print? input]
   (if-not print?
     (data-transform input (completing (partial max 0)))
     (data-transform
       input
       (fn max-reducing-function
         ([]
          (println "zero args" 0) 0)
         ([result]
          (println "one arg: " result) result)
         ([input result]
          (println "two args: " input result)
          (max input result)))))))

(defn part2
  ([] (part2 false))
  ([print?] (part2 print? day1-input))
  ([print? input]
   (data-transform
     input
     (fn aggregate-sum-and-sort
       ([]
        (when print? (println "zero args" []))
        [])
       ([result]
        (when print? (println "one arg: " result))
        (->> result sort (take-last 3) (apply +)))
       ([input result]
        (when print? (println "two args: " input result))
        (conj input result))))))

(comment
  To see the reducing functions process the sample data load the namespace
  and evaluate these forms
  (part1 true sample)
  (part2 true sample))