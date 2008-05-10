/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file $(NAMEH)
 * \brief заголовок класса $(NAME)
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _$(NAME)_H_$(UUID)_INCLUDED_
#define _$(NAME)_H_$(UUID)_INCLUDED_

/*!
 * \brief
 * \todo комментировать
 */
class $(NAME)
{
  $(NAME)(const $(NAME)& obj);
  $(NAME)& operator=(const $(NAME)& obj);
  
public:
  $(NAME)()
  {
  }
  
  ~$(NAME)()
  {
  }
};//class $(NAME)

#endif //_$(NAME)_H_$(UUID)_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
