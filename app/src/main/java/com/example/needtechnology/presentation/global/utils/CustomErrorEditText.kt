package com.example.needtechnology.presentation.global.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ru.diitcenter.orion.R


const val CLICKABLE_DRAWABLE_END_VISIBLE = 1
const val CLICKABLE_DRAWABLE_END_GONE = 2


/*По какой-то не понятной причине, при заходе или возвращении на экран, текст из нижней в xml кастомной вьюшки,
один раз проходится по TextWatcher'ам верхних в xml кастомных вьюшек, это происходит только с подобными кастомными вьюшками,
я создавал другую кастомную вью, где просто EditText, я просто инициализирую EditText, и вставляю с помощью addView, и больше ничего,
и так же объявляю 2 и более вью в xml, и все равно происходит та же дичь, я так и не понял в чем причина
Вот вопрос на стеке, если вы знаете решение исправьте пожалуйста, и распишите решение в вопросе

https://ru.stackoverflow.com/questions/962462/textwatcher-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0%D0%B5%D1%82-%D0%BD%D0%B5%D0%B2%D0%B5%D1%80%D0%BD%D0%BE-%D1%81-edittext-%D0%BA%D0%BE%D1%82%D0%BE%D1%80%D1%8B%D0%B9-%D0%BD%D0%B0%D1%85%D0%BE%D0%B4%D0%B8%D1%82%D1%81%D1%8F-%D0%B2%D0%BD%D1%83%D1%82%D1%80%D0%B8-%D0%BA%D0%B0%D1%81%D1%82%D0%BE%D0%BC%D0%BD%D0%BE%D0%B9-view

Сейчас эта проблема решается с помощью moxy, посмотрите, например, на экран SignInFragment, в его view интефейсе, есть
два метода начинающеся с apply, вот их я вызываю через презентер в onDestroyView, что бы при возвращении вставилось последнее введенное значение,
 знаю, как-то замудренно, но это решает проблему. Можно конечно не вызывать второй apply метод, все будет работать также,
 но именно это и странно, по какой-то причине, она еще и запонимает текст введенный в нижнюю в xml кастомную вью,
 я создал второй метод просто для наглядности, что бы невозникло непоняток.

 ЗЫ Такого не возникает, если использовать простой EditText*/
class CustomErrorEditText : FrameLayout {
    lateinit var textInput: EditText
    lateinit var textError: TextView
    lateinit var clickableDrawableEnd: ImageButton
    private lateinit var iconAboveError: ImageView

    private var customErrorInputType = 0
    private var customPaddingEnd = 0
    private var customPaddingStart = 0
    private var customPaddingTop = 0
    private var customPaddingBottom = 0
    private var digits: String? = ""
    private var hint: String? = ""
    private var text: String? = ""
    private var clickableDrawableEndVisibility = CLICKABLE_DRAWABLE_END_GONE
    private var clickableDrawableEndAlpha = 0f
    private var doAfterTextChanged: (text: String) -> Unit = {}
    private var drawableError: Drawable? = null
    private var drawableSelector: Drawable? = null
    var clickableDrawableEndResId = 0
    var isError = false

    @JvmOverloads
    constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleVal: Int = 0
    ) : super(context, attributeSet, defStyleVal) {
        initAttr(attributeSet)
        initView()
    }


    private fun initAttr(attributeSet: AttributeSet?) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.CustomErrorEditText)
        typedArray?.run {
            customPaddingEnd =
                getDimension(
                    R.styleable.CustomErrorEditText_PaddingEnd,
                    convertDpToPixel(0f, context)
                ).toInt()

            customPaddingStart =
                getDimension(
                    R.styleable.CustomErrorEditText_PaddingStart,
                    convertDpToPixel(0f, context)
                ).toInt()

            customPaddingTop =
                getDimension(
                    R.styleable.CustomErrorEditText_PaddingTop,
                    convertDpToPixel(6f, context)
                ).toInt()

            customPaddingBottom =
                getDimension(
                    R.styleable.CustomErrorEditText_PaddingBottom,
                    convertDpToPixel(15f, context)
                ).toInt()

            customErrorInputType = getInteger(
                R.styleable.CustomErrorEditText_CustomErrorInputType,
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            )

            digits = getString(R.styleable.CustomErrorEditText_CustomErrorDigits)
            hint = getString(R.styleable.CustomErrorEditText_CustomErrorHint)
            text = getString(R.styleable.CustomErrorEditText_CustomErrorText)
            clickableDrawableEndVisibility = getInteger(
                R.styleable.CustomErrorEditText_ClickableDrawableEndVisibility,
                CLICKABLE_DRAWABLE_END_GONE
            )
            clickableDrawableEndResId =
                getResourceId(R.styleable.CustomErrorEditText_ClickableDrawableEndSrc, 0)
            clickableDrawableEndAlpha =
                getFloat(R.styleable.CustomErrorEditText_ClickableDrawableEndAlpha, 0f)


            val drawableNormalRef = getResourceId(
                R.styleable.CustomErrorEditText_CustomErrorDrawableNormal,
                R.drawable.edittext_underline_selector
            )
            drawableSelector = ContextCompat.getDrawable(context, drawableNormalRef)
            val drawableErrorRef = getResourceId(
                R.styleable.CustomErrorEditText_CustomErrorDrawableError,
                R.drawable.edittext_underline_error
            )
            drawableError = ContextCompat.getDrawable(context, drawableErrorRef)
        }
        typedArray.recycle()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.custom_error_edit_text, this, true)
        textInput = findViewById(R.id.textInput)
        textError = findViewById(R.id.textError)
        clickableDrawableEnd = findViewById(R.id.clickableDrawableEnd)
        iconAboveError = findViewById(R.id.icon_above_error)

        clickableDrawableEnd.run {
            visibility =
                if (clickableDrawableEndVisibility == CLICKABLE_DRAWABLE_END_VISIBLE) View.VISIBLE
                else View.GONE
            setImageResource(clickableDrawableEndResId)
            alpha = clickableDrawableEndAlpha
        }

        textInput.run {
            setPadding(
                customPaddingStart,
                customPaddingTop,
                customPaddingEnd,
                customPaddingBottom
            )
            hint = this@CustomErrorEditText.hint
            setText(this@CustomErrorEditText.text)
            digits?.run { textInput.keyListener = DigitsKeyListener.getInstance(this) }
            afterTextChanged {
                doAfterTextChanged(it)
                if (isError) {
                    isError = false
                    background = drawableSelector
                }
            }
            post { inputType = customErrorInputType }
            background = drawableSelector
        }

        textError.applyMargin(0, 0, customPaddingEnd, 0)
    }

    fun setIcon(iconEnd: Int) {
        textInput.setRightDrawable(iconEnd)
        textError.visibility = View.GONE
        iconAboveError.visibility = View.GONE

    }

    fun deleteIcon() {
        textInput.setRightDrawable(0)
        textError.visibility = View.GONE
        iconAboveError.visibility = View.GONE
    }

    fun setError(
        error: CharSequence = "",
        iconEnd: Int = R.drawable.popup_warning_ic,
        errorPopupVisibility: Int = View.VISIBLE
    ) {
        isError = true
        textInput.setRightDrawable(iconEnd)
        textError.text = error
        textError.visibility = errorPopupVisibility
        iconAboveError.visibility = errorPopupVisibility
        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT)
            textInput.background = drawableError
    }

    fun drawableEndClickListener(lambda: () -> Unit) {
        clickableDrawableEnd.setOnClickListener {
            lambda()
        }
    }

    fun doAfterTextChanged(doAfterTextChanged: (text: String) -> Unit) {
        this.doAfterTextChanged = doAfterTextChanged
    }

}

fun EditText.afterTextChanged(lambda: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            lambda(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    })
}

fun EditText.setRightDrawable(drawableId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableId, 0)
}

fun convertDpToPixel(dp: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi / 160f)
}

internal fun View.applyMargin(
    start: Int? = null,
    top: Int? = null,
    end: Int? = null,
    bottom: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
            marginStart = start ?: marginStart
            topMargin = top ?: topMargin
            marginEnd = end ?: marginEnd
            bottomMargin = bottom ?: bottomMargin
        }
    }
}
