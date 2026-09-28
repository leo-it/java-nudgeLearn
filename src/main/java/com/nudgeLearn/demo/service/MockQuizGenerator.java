package com.nudgeLearn.demo.service;

import com.nudgeLearn.demo.domain.Topic;
import com.nudgeLearn.demo.dto.GeneratedQuestion;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MockQuizGenerator implements QuizGenerator {

	@Override
	public List<GeneratedQuestion> generate(Topic topic, int count) {
		String subject = topic.getTitle();
		List<GeneratedQuestion> bank = List.of(
				new GeneratedQuestion(
						"¿Cuál es una buena forma de empezar a estudiar " + subject + "?",
						List.of(
								"Repasar un poco todos los días",
								"Estudiar 8 horas una sola vez al mes",
								"Memorizar sin practicar",
								"Evitar los errores para no aprender de ellos"),
						0,
						"El active recall funciona mejor con práctica frecuente y espaciada. Un rato cada día rinde más que una sesión maratónica."),
				new GeneratedQuestion(
						"Si fallás una pregunta de " + subject + ", ¿qué conviene hacer?",
						List.of(
								"Ignorarla y pasar a otro tema",
								"Leer la explicación y volver a intentarla más tarde",
								"Borrar el progreso y empezar de cero",
								"Cambiar de tema para siempre"),
						1,
						"Fallar es parte del aprendizaje. La explicación inmediata + un repaso posterior (spaced repetition) consolida el concepto."),
				new GeneratedQuestion(
						"¿Qué significa spaced repetition aplicado a " + subject + "?",
						List.of(
								"Hacer el mismo quiz 20 veces seguidas",
								"Repasar justo cuando estás por olvidar",
								"Estudiar solo lo que ya sabés perfecto",
								"Dejar pasar un año entre cada quiz"),
						1,
						"Spaced repetition programa el próximo repaso cerca del momento en que la memoria se debilita, no demasiado pronto ni demasiado tarde."),
				new GeneratedQuestion(
						"Al responder un quiz de " + subject + ", ¿qué es active recall?",
						List.of(
								"Leer la teoría otra vez sin preguntarte nada",
								"Subrayar el apunte en varios colores",
								"Intentar recuperar la respuesta de memoria",
								"Copiar la respuesta correcta de internet"),
						2,
						"Active recall es esforzarte por recordar. Generar la respuesta vos fortalece más la memoria que volver a leer el material."),
				new GeneratedQuestion(
						"¿Cuál es un objetivo razonable para un micro-quiz de " + subject + "?",
						List.of(
								"Terminar todo el temario en 2 minutos",
								"Sacar 10/10 siempre, sin fallar nunca",
								"Practicar 5 a 10 preguntas y aprender de los errores",
								"Evitar las preguntas difíciles"),
						2,
						"Un micro-quiz corto (5-10 preguntas) sirve para practicar seguido. Los errores, con explicación, son el motor del progreso."));

		return bank.stream().limit(count).toList();
	}
}
