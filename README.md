# Poxy (NeoForge)

NeoForge 1.21.1 port of [Poxy](https://github.com/Jokypond/Poxy) — Voxy's
Android compat patches (LMDB storage backend + `libc.so` linkage), ported
from Fabric. Meant to run alongside Roxy, which loads the original,
unmodified Fabric Voxy jar on NeoForge — since Voxy's own classes
(`me.cortex.voxy.*`) are untouched either way, this port only needed a new
loader shell, not a Voxy port of its own. Both mixins are line-for-line
ports of the originals; only the package changed.

## What it does (same as upstream Poxy)

- `StorageConfigUtilMixin` — injects into
  `me.cortex.voxy.common.StorageConfigUtil#createDefaultSerializer`, forcing
  the compression delegate to `LMDBStorageBackend.Config` instead of
  whatever Voxy defaults to.
- `ThreadUtilsMixin` — rewrites the `"libc.so.6"` constant in
  `me.cortex.voxy.common.util.ThreadUtils`'s `<clinit>` to `"libc.so"`
  (Android has no glibc, only its own libc).
- `PoxyMixinPlugin` — gates both mixins behind a runtime check for
  `/system/lib64/libandroid.so`, so this is a no-op off Android.

## What's NOT ported yet: the native libraries

Poxy's repo has a `natives/` folder with prebuilt Android ARM64 `.so`
files for ZSTD and LMDB, bundled as raw jar resources — no Java code
loads them explicitly (LWJGL's own native-library resolution picks them
up by path convention). Copy that folder over as-is:

```bash
cp -r ~/Poxy/natives ~/poxy-neoforge/src/main/resources/
```

If Gradle's resource processing or the jar task doesn't pick up
`natives/` automatically, add:

```gradle
sourceSets.main.resources.srcDir 'src/main/resources/natives'
```

## Before building

1. Get a Voxy jar for the compile classpath (Roxy needs one anyway):
   ```bash
   mkdir -p libs
   cp /path/to/voxy-*.jar libs/voxy.jar
   ```
2. Check `gradle.properties` — `neo_version` and the Parchment versions are
   best-guesses; bump them if Gradle complains they don't exist. Check
   https://projects.neoforged.net/neoforged/neoforge for the current 21.1.x
   build.
3. Copy `natives/` as above.

## Build

Locally (no wrapper is committed — install Gradle yourself, e.g.
`pkg install gradle` on Termux):

```bash
gradle build
# output: build/libs/poxy-neoforge-1.0.0.jar
```

Drop it in `.minecraft/mods/` alongside Roxy, the Voxy Fabric jar Roxy
needs, and NeoForge 21.1.x for MC 1.21.1.

### CI

`.github/workflows/build.yml` builds the jar on every push and uploads it
as a workflow artifact. It needs a Voxy jar at compile time and never
commits one (Voxy is All-Rights-Reserved) — set a repo variable
`VOXY_JAR_URL` (Settings → Secrets and variables → Actions → Variables) to
a direct download link, or trigger the workflow manually via
"Run workflow" and pass the URL as an input.

## License

GPLv3, matching upstream Poxy. Does not redistribute Voxy or Roxy.
