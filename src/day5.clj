(ns day5
  (:require [clojure.string :as str]))

(def sample "    [D]    \n[N] [C]    \n[Z] [M] [P]\n 1   2   3 \n\nmove 1 from 2 to 1\nmove 3 from 1 to 3\nmove 2 from 2 to 1\nmove 1 from 1 to 2")
(def day5-input (slurp "./src/day5-input.txt"))

(defn parse-starting-configuration
  [starting-string]
  (reduce #(update %1 (first %2) conj (second %2)) {}
          (reverse (into [] (comp (map #(partition 3 4 %))
                                  (map (partial map #(nth % 1)))
                                  (mapcat (partial map-indexed (partial vector)))
                                  (remove (comp #{\space} second))
                                  (map (juxt (comp inc first) second)))
                         (butlast starting-string)))))

(defn parse-instruction [instruction]
  (map #(Integer/parseInt %)
       (rest (re-find #"move (\d+) from (\d+) to (\d+)"
                      instruction))))

(defmulti move (fn [& args] (first args)))
(defmethod move :individual
  [_dispatch m [n from to]]
  (let [mover #(let [[popped] (get % from)]
                 (-> (update % from pop)
                     (update to conj popped)))]
    (nth (iterate mover m) n)))

(defmethod move :collective
  [_dispatch m [n from to]]
  (let [[popped remaining] (split-at n (get m from))]
    (-> (assoc m from remaining)
        (update to #(flatten (conj % popped))))))

(defn runner [reducing-fn]
  (let [[start & instructions] (->> day5-input str/split-lines (partition-by #(= % "")))
        instructions (map parse-instruction (last instructions))]
    (->> (reduce reducing-fn (parse-starting-configuration start) instructions)
         (sort-by first)
         (map second)
         (map first)
         (apply str))))

(defn part1 [] (runner (partial move :individual)))
(defn part2 [] (runner (partial move :collective)))

(part1)
(part2)
