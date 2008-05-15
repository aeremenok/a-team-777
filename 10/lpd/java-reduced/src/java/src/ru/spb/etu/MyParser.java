package ru.spb.etu;

class MyParser
    extends Parser
{
    public MyParser(
        Scanner scanner )
    {
        super( scanner );
    }

    @Override
    void accessSpecifier()
    {
        // TODO Auto-generated method stub
        super.accessSpecifier();
        System.out.println( "MyParser.accessSpecifier()" );
    }

    @Override
    void additiveExpression()
    {
        // TODO Auto-generated method stub
        super.additiveExpression();
    }

    @Override
    void andExpression()
    {
        // TODO Auto-generated method stub
        super.andExpression();
    }

    @Override
    void assignmentOperator()
    {
        // TODO Auto-generated method stub
        super.assignmentOperator();
    }

    @Override
    void classBody()
    {
        // TODO Auto-generated method stub
        super.classBody();
    }

    @Override
    void classDeclaration()
    {
        // TODO Auto-generated method stub
        super.classDeclaration();
    }

    @Override
    void CompilationUnit()
    {
        // TODO Auto-generated method stub
        super.CompilationUnit();
    }

    @Override
    void conditionalAndExpression()
    {
        // TODO Auto-generated method stub
        super.conditionalAndExpression();
    }

    @Override
    void conditionalOrExpression()
    {
        // TODO Auto-generated method stub
        super.conditionalOrExpression();
    }

    @Override
    void equalityExpression()
    {
        // TODO Auto-generated method stub
        super.equalityExpression();
    }

    @Override
    void exclusiveOrExpression()
    {
        // TODO Auto-generated method stub
        super.exclusiveOrExpression();
    }

    @Override
    void expression()
    {
        // TODO Auto-generated method stub
        super.expression();
    }

    @Override
    void expressionName()
    {
        // TODO Auto-generated method stub
        super.expressionName();
    }

    @Override
    void formalParameterList()
    {
        // TODO Auto-generated method stub
        super.formalParameterList();
    }

    @Override
    void inclusiveOrExpression()
    {
        // TODO Auto-generated method stub
        super.inclusiveOrExpression();
    }

    @Override
    void interfaceBody()
    {
        // TODO Auto-generated method stub
        super.interfaceBody();
    }

    @Override
    void interfaceDeclaration()
    {
        // TODO Auto-generated method stub
        super.interfaceDeclaration();
    }

    @Override
    void multiplicativeExpression()
    {
        // TODO Auto-generated method stub
        super.multiplicativeExpression();
    }

    @Override
    void relationalExpression()
    {
        // TODO Auto-generated method stub
        super.relationalExpression();
    }

    @Override
    void shiftExpression()
    {
        // TODO Auto-generated method stub
        super.shiftExpression();
    }

    @Override
    void statement()
    {
        // TODO Auto-generated method stub
        super.statement();
    }

    @Override
    void type()
    {
        // TODO Auto-generated method stub
        super.type();
    }

    @Override
    void typeDeclaration()
    {
        // TODO Auto-generated method stub
        System.out.println( "MyParser.typeDeclaration(), last: " + t.val + ", ahead: " + la.val );
        super.typeDeclaration();
    }

    @Override
    void unaryExpression()
    {
        // TODO Auto-generated method stub
        super.unaryExpression();
    }

}
