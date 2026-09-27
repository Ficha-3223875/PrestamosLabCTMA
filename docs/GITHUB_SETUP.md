# Configuración GitHub obligatoria

1. Subir este mismo repositorio; no crear un proyecto Android nuevo.
2. Crear/actualizar GitHub Project con columnas Backlog / Ready / In Progress / Review / Done.
3. Crear Issues HU-01..HU-10 usando `.github/ISSUE_TEMPLATE/user-story.md` y copiar criterios desde `PRODUCT_BACKLOG.md`.
4. Crear Issues BUG solo para fallas realmente reproduccidas.
5. Trabajar en ramas `feature/hu-XX-descripcion` o `fix/bug-XX-descripcion`.
6. Abrir PR con `Closes #N`, riesgos y TC relacionados.
7. Esperar Actions: build + unit tests + lint.
8. Hacer code review, corregir y mergear a `main`.
9. Crear tags/incrementos: `v0.2.0` a `v0.6.0` si el instructor mantiene la convención sugerida.
10. Adjuntar evidencia real de ejecución/screenshots en Issues/PR o carpeta acordada.

`CODEOWNERS` usa `@solorzanos213` como responsable inicial; si el trabajo es grupal, agregar los usuarios GitHub del equipo.
