(ns com.adham-omran.ir
  (:gen-class)
  (:require
   [clojure.string :as str]
   [clojure.tools.cli :refer [parse-opts]])
  (:import
   [java.net InetAddress]))

(def cli-options
  [ ;; First three strings describe a short-option, long-option with optional
   ;; example argument description, and a description. All three are optional
   ;; and positional.
   ["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-H" "--hostname HOST" "Remote host"
    :default (InetAddress/getByName "localhost")
    ;; Specify a string to output in the default column in the options summary
    ;; if the default value's string representation is very ugly
    :default-desc "localhost"
    :parse-fn #(InetAddress/getByName %)]
   ;; If no required argument description is given, the option is assumed to
   ;; be a boolean option defaulting to nil
   [nil "--detach" "Detach from controlling process"]
   ["-v" nil "Verbosity level; may be specified multiple times to increase value"
    ;; If no long-option is specified, an option :id must be given
    :id :verbosity
    :default 0
    ;; Use :update-fn to create non-idempotent options (:default is applied first)
    :update-fn inc]
   ["-f" "--file NAME" "File names to read"
    :multi true       ; use :update-fn to combine multiple instance of -f/--file
    :default []
    ;; with :multi true, the :update-fn is passed both the existing parsed
    ;; value(s) and the new parsed value from each option
    :update-fn conj]
   ["-t" nil "Timeout in seconds"
    ;; Since there is no long option, :id is required...
    :id :timeout
    ;; ...and we require an argument to be provided:
    :required "TIMEOUT"
    ;; parse-long was added in Clojure 1.11:
    :parse-fn parse-long]
   ;; A boolean option that can explicitly be set to false
   ["-g" "--gui"
    :id :gui
    :desc "Launch in GUI mode"]
   ["-d" "--[no-]daemon" "Daemonize the process" :default true]
   ["-h" "--help"]])

(defn usage [options-summary]
  (->> ["This is my program. There are many like it, but this one is mine."
        ""
        "Usage: program-name [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  start    Start a new server"
        "  stop     Stop an existing server"
        "  status   Print a server's status"
        ""
        "Please refer to the manual page for more information."]
       (str/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with an error message, and optional ok status), or a map
  indicating the action the program should take and the options provided."
  [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options)                   ; help => exit OK with usage summary
      {:exit-message (usage summary) :ok? true}

      (:gui options)
      {:gui? true}

      errors                         ; errors => exit with description of errors
      {:exit-message (error-msg errors)}

      ;; custom validation on arguments
      (and (= 1 (count arguments))
           (#{"start" "stop" "status"} (first arguments)))
      {:action (first arguments) :options options}
      :else                ; failed custom validation => exit with usage summary
      {:exit-message (usage summary)})))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;; TODO: Use https://github.com/clojure/tools.cli
  (let [{:keys [action exit-message ok? gui?]} (validate-args args)]
    (when gui?
      (println "Starting GUI mode... Experimental...")
      (exit 0 "Leaving."))
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (case action
        "start" (println :start)
        "stop" (println :stop)
        "status" (println :status)))))


;; We validate then set what to do for -main to run
