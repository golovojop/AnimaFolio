package k.s.yarlykov.animafolio.ui.photogallery

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout

class ParallaxListItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr), VerticalMotionListener {

    // Максимальное значение Y верхнего левого угла элемента списка внутри RecyclerView
    private var maxOffset = minStartOffset
    // Длина пути элемента списка (точнее верхнего левого угла ) от его начальной
    // позиции (при создании RecyclerView) до полного ухода элемента за верхнюю границу
    // RecyclerView. Пока элемент движется по этому пути выполняется Motion анимация картинки.
    // То есть interpolationLength = start_offset + view_height
    private var interpolationLength = 0f
    // Это для отладки
    private var isShowProgress = false

    /**
     * @offset - текущая Y-координата верхнего левого угла элемента списка. Может
     * иметь отрицательное значение при скроллинге и выходе элемента за верхнюю
     * границу родительского RecyclerView. Измераяется относительно верхней
     * границы родительского RecyclerView.
     */
    override fun onOffsetChanged(offset: Float) {

        /**
         * Для элементов списка с индексом больше 0
         */
        if (offset > maxOffset) {
            maxOffset = offset
        }

        interpolationLength = maxOffset + height

        if (interpolationLength != 0f && offset >= -height) {
            progress = (interpolationLength - (height.toFloat() + offset)) / interpolationLength
        }

        /**
         * Debug message
         */
        if (isShowProgress) {
            showProgress(offset)
        }
    }

    override fun setDebugFlag(flag: Boolean) {
        isShowProgress = flag
    }

    override fun onUpdateMaxOffset(maxOffset: Float) {
        this.maxOffset = maxOffset
        this.interpolationLength = maxOffset + height
    }

    override fun showProgress(offset: Float) {
        System.out.println("APP_TAG: ${hashCode().toString(16)}: interpolationLength=$interpolationLength, maxOffset=$maxOffset, offset=$offset, height=$height, progress=$progress")
    }
}

interface VerticalMotionListener {

    /**
     * Минимальное Y-смещение элемента внутри RecyclerView.
     * Самый первый (верхний) элемент RecyclerView имеет такое
     * смещение при создании RecyclerView, то есть до прокрутки
     */
    val minStartOffset: Float
        get() = 0f

    val invalidMaxOffset: Float
        get() = Float.NEGATIVE_INFINITY

    fun onOffsetChanged(offset: Float)
    fun onUpdateMaxOffset(maxOffset: Float)
    fun setDebugFlag(flag: Boolean)
    fun showProgress(offset: Float)
}