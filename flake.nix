{
  description = "A Nix-flake-based Clojure development environment";

  inputs.nixpkgs.url = "github:nixos/nixpkgs/nixos-24.11";

  outputs = { self, nixpkgs }:

    let
      javaVersion = 21;
      overlays = [
        (final: prev: rec {
          jdk = prev."jdk${toString javaVersion}";
          boot = prev.boot.override { inherit jdk; };
          clojure = prev.clojure.override { inherit jdk; };
          leiningen = prev.leiningen.override { inherit jdk; };
        })
      ];
      supportedSystems = [ "x86_64-linux" "aarch64-linux" "x86_64-darwin" "aarch64-darwin" ];
      forEachSupportedSystem = f: nixpkgs.lib.genAttrs supportedSystems (system: f {
        pkgs = import nixpkgs { inherit overlays system; };
      });
    in
      {
        devShells = forEachSupportedSystem ({ pkgs }: {
          default = pkgs.mkShell {
            packages = with pkgs; [
              # Clojure
              clojure-lsp
              clojure
              babashka
              jdk
              cljfmt
              # Node
              nodejs_22
            ];
            LOCALE_ARCHIVE = "${pkgs.glibcLocales}/lib/locale/locale-archive";
            LD_LIBRARY_PATH = "${pkgs.libGL}/lib:${pkgs.gtk3}/lib:${pkgs.glib.out}/lib:${pkgs.xorg.libXtst}/lib";
          };
        });
      };
  nixConfig.bash-prompt = "(nix) \\w \\$ ";
}
