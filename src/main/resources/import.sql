INSERT INTO school.grup
(grupId,grupName, grupMentor)
VALUES
(1,'C-161','Moraru Victor'),
(2,'C-161','Moraru Nicolae'),
(3,'C-162','Munteanu Silvia');

INSERT INTO school.student
(id, firstName, lastName, sex, grupId_grupId)
VALUES
(1,'Boris','Cazacu','M',2),
-- (1,'Nicu','Boievicu','M',2),
(2,'Ion','Munteanu','M',1);