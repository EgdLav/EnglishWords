package com.example.englishwords

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.englishwords.databinding.ActivityEnglishWordsBinding

class MainActivity : AppCompatActivity() {
    //    private lateinit var binding: ActivityEnglishWordsBinding
    private var _binding: ActivityEnglishWordsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("binding ")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityEnglishWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)

        with(binding) {
            btnContinue.setOnClickListener {
                layoutResult.isVisible = false
                markAnswerNeutral(layoutAnswer1, tvAnswerNumber1, tvAnswerText1)
                markAnswerNeutral(layoutAnswer2, tvAnswerNumber2, tvAnswerText2)
                markAnswerNeutral(layoutAnswer3, tvAnswerNumber3, tvAnswerText3)
                markAnswerNeutral(layoutAnswer4, tvAnswerNumber4, tvAnswerText4)
                showNextQuestion(trainer)
            }

            btnSkip.setOnClickListener {
                showNextQuestion(trainer)
            }
        }

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    private fun showNextQuestion(trainer: LearnWordsTrainer) {
        val firstQuestion: Question? = trainer.getNextQuestion()
        with(binding) {
            if (firstQuestion == null || firstQuestion.variants.size < NUMBER_OF_ANSWERS) {
                tvQuestionWord.isVisible = false
                layoutVariants.isVisible = false
                btnSkip.text = "Complete"
            } else {
                btnSkip.isVisible = true
                tvQuestionWord.isVisible = true

                tvQuestionWord.text = firstQuestion?.correctAnswer?.original

                tvAnswerText1.text = firstQuestion.variants[0].translate
                tvAnswerText2.text = firstQuestion.variants[1].translate
                tvAnswerText3.text = firstQuestion.variants[2].translate
                tvAnswerText4.text = firstQuestion.variants[3].translate

                layoutAnswer1.setOnClickListener {
                    if (trainer.checkAnswer(0)) {
                        markAnswerCorrect(layoutAnswer1, tvAnswerNumber1, tvAnswerText1)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer1, tvAnswerNumber1, tvAnswerText1)
                        showResultMessage(false)
                    }
                }
                layoutAnswer2.setOnClickListener {
                    if (trainer.checkAnswer(1)) {
                        markAnswerCorrect(layoutAnswer2, tvAnswerNumber2, tvAnswerText2)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer2, tvAnswerNumber2, tvAnswerText2)
                        showResultMessage(false)
                    }
                }
                layoutAnswer3.setOnClickListener {
                    if (trainer.checkAnswer(2)) {
                        markAnswerCorrect(layoutAnswer3, tvAnswerNumber3, tvAnswerText3)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer3, tvAnswerNumber3, tvAnswerText3)
                        showResultMessage(false)
                    }
                }
                layoutAnswer4.setOnClickListener {
                    if (trainer.checkAnswer(3)) {
                        markAnswerCorrect(layoutAnswer4, tvAnswerNumber4, tvAnswerText4)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer4, tvAnswerNumber4, tvAnswerText4)
                        showResultMessage(false)
                    }
                }
            }
        }
    }

    private fun markAnswerNeutral(
        layoutAnswer: LinearLayout,
        tvAnswerNumber: TextView,
        tvAnswerText: TextView,
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers
        )
        tvAnswerText.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.textVariantsColor
            )
        )
        tvAnswerNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_numbers
        )
        tvAnswerNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.textVariantsColor
            )
        )
        binding.layoutResult.isVisible = false
        binding.btnSkip.isVisible = true
    }

    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvAnswerNumber: TextView,
        tvAnswerText: TextView,
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_correct
        )
        tvAnswerNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_numbers_correct
        )
        tvAnswerNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )
        tvAnswerText.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswerColor
            )
        )

//            layoutResult.setBackgroundColor(
//                ContextCompat.getColor(
//                    this@MainActivity,
//                    R.color.correctAnswerColor
//                )
//            )
//            ivResultIcon.setImageDrawable(
//                ContextCompat.getDrawable(
//                    this@MainActivity,
//                    R.drawable.ic_correct
//                )
//            )
//            tvResultText.text = resources.getString(R.string.title_correct)
//
//            btnContinue.setTextColor(
//                ContextCompat.getColor(
//                    this@MainActivity,
//                    R.color.correctAnswerColor
//                )
//            )

    }

    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvAnswerNumber: TextView,
        tvAnswerText: TextView,
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_wrong
        )
        tvAnswerNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_numbers_wrong
        )
        tvAnswerNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )
        tvAnswerText.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswerColor
            )
        )

    }

    private fun showResultMessage(isCorrect: Boolean) {
        val color: Int
        val messageText: String
        val resultIcon: Int

        if (isCorrect) {
            color = ContextCompat.getColor(this@MainActivity, R.color.correctAnswerColor)
            resultIcon = R.drawable.ic_correct
            messageText = ContextCompat.getString(this, R.string.title_correct)
        } else {
            color = ContextCompat.getColor(this@MainActivity, R.color.wrongAnswerColor)
            resultIcon = R.drawable.ic_wrong
            messageText = ContextCompat.getString(this, R.string.title_wrong)
        }
        with(binding) {
            btnContinue.setTextColor(color)
            ivResultIcon.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, resultIcon))
            tvResultText.text = messageText
            layoutResult.setBackgroundColor(color)
            btnSkip.isVisible = false
            layoutResult.isVisible = true
        }
    }


}