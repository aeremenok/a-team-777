package ru.spb.messages;

/**
 * индивидуальный обработчик пришедших ответов
 * 
 * @author eav
 */
public interface ReplyListener
{
    /**
     * ошибка на сервере
     * 
     * @param numericReply сообщение об ошибке
     */
    void onFailure(
        NumericReply numericReply );

    /**
     * запрос успешно обработан
     * 
     * @param numericReply ответ
     */
    void onSuccess(
        NumericReply numericReply );
}
