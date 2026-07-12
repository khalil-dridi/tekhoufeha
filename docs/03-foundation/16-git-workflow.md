# Git Workflow

**Project:** TekhouFeha

**Version:** 1.0

**Status:** Approved

---

# 1. Purpose

This document defines the Git workflow used during the development of TekhouFeha.

The objective is to maintain a clean and understandable Git history.

---

# 2. Repository

Platform

- GitHub

Repository Type

- Private (during development)

Future

- Public (after project completion)

---

# 3. Branch Strategy

Current Strategy (MVP)

Only one branch is used:

main

Future Strategy

As the project grows, the following branches may be introduced:

- develop
- feature/*
- bugfix/*
- hotfix/*
- release/*

---

# 4. Commit Convention

The project follows Conventional Commits.

Available commit types:

- feat
- fix
- docs
- refactor
- style
- test
- chore
- perf
- ci

Examples

feat(auth): implement user registration

feat(product): add create product endpoint

fix(auth): validate email format

docs: update repository architecture

refactor(user): simplify service layer

test(product): add unit tests

chore: update dependencies

---

# 5. Commit Rules

A commit should:

- Represent one logical change.
- Be small and focused.
- Compile successfully.
- Not include unrelated changes.

Avoid large commits.

---

# 6. Development Workflow

For every feature:

1. Analyze requirements
2. Update UML if necessary
3. Implement backend
4. Write backend tests
5. Implement frontend
6. Write frontend tests
7. Manual testing
8. Commit
9. Push

---

# 7. Commit Frequency

Commit regularly.

Recommended:

- One commit per completed task.
- One commit per bug fix.
- One commit per documentation update.

---

# 8. Files That Must Never Be Committed

Examples:

- .env
- application-local.yml
- passwords
- API keys
- IDE temporary files
- log files

These files must be ignored using .gitignore.

---

# 9. Merge Strategy

Current MVP

Development is performed directly on the main branch.

Future

Feature branches and Pull Requests may be introduced when collaborating with other developers.

---

# 10. Versioning

The project follows Semantic Versioning.

Format:

MAJOR.MINOR.PATCH

Examples:

v0.1.0

v0.2.0

v0.3.0

v1.0.0

---

# 11. Git Tags

Stable versions should be tagged.

Example:

git tag v0.1.0

git push origin v0.1.0

---

# 12. Best Practices

- Commit often.
- Write meaningful commit messages.
- Keep commits atomic.
- Test before committing.
- Never commit secrets.
- Keep the repository clean.

---

# 13. Future Improvements

When the project becomes larger, Git Flow may be adopted.

Possible workflow:

main

↓

develop

↓

feature branches

↓

Pull Request

↓

Merge

This workflow is intentionally postponed to keep the MVP simple.

---

# Document History

| Version | Date | Author | Description |
|----------|------|--------|-------------|
| 1.0 | July 2026 | Project Team | Initial version |