<?php

/**
 * This is the model class for table "tlogin".
 *
 * The followings are the available columns in table 'tlogin':
 * @property string $email
 * @property string $password
 * @property string $fName
 * @property string $mName
 * @property string $lName
 * @property string $institution
 * @property string $area
 * @property string $phone
 * @property string $mobile
 * @property string $role
 * @property string $regDate
 * @property string $actCode
 * @property integer $accStatus
 * @property string $lastLogin
 */
class Login extends CActiveRecord
{
	public $verifyPassword; // holds for comparing with password
	public $verifyCode; // holds for captcha

	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'tlogin';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('email, password, fName, lName, institution, area, mobile, role, regDate, actCode, lastLogin', 'required'),
			array('accStatus', 'numerical', 'integerOnly'=>true),
			array('email, password, verifyPassword', 'length', 'max'=>100),
			//array('email','size', 'max'=>50),
			array('fName, mName, lName, phone, mobile, actCode', 'length', 'max'=>50),
			array('institution, area', 'length', 'max'=>200),
			array('role', 'length', 'max'=>20),
			array('email','email'), // make sure email is a valid email
			//array('password','password'),
			//array('email','filter','strtolower'),
			// The following rule is used by search().
			// @todo Please remove those attributes that should not be searched.
			array('email, password, fName, mName, lName, institution, area, phone, mobile, role, regDate, actCode, accStatus, lastLogin', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'email' => 'Email',
			'password' => 'Password',
			'fName' => 'First Name',
			'mName' => 'Middle Name',
			'lName' => 'Last Name',
			'institution' => 'Institution',
			'area' => 'Area',
			'phone' => 'Phone',
			'mobile' => 'Mobile',
			'role' => 'Role',
			'regDate' => 'Reg Date',
			'actCode' => 'Act Code',
			'accStatus' => 'Acc Status',
			'lastLogin' => 'Last Login',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 *
	 * Typical usecase:
	 * - Initialize the model fields with values from filter form.
	 * - Execute this method to get CActiveDataProvider instance which will filter
	 * models according to data in model fields.
	 * - Pass data provider to CGridView, CListView or any similar widget.
	 *
	 * @return CActiveDataProvider the data provider that can return the models
	 * based on the search/filter conditions.
	 */
	public function search()
	{
		// @todo Please modify the following code to remove attributes that should not be searched.

		$criteria=new CDbCriteria;

		$criteria->compare('email',$this->email,true);
		$criteria->compare('password',$this->password,true);
		$criteria->compare('fName',$this->fName,true);
		$criteria->compare('mName',$this->mName,true);
		$criteria->compare('lName',$this->lName,true);
		$criteria->compare('institution',$this->institution,true);
		$criteria->compare('area',$this->area,true);
		$criteria->compare('phone',$this->phone,true);
		$criteria->compare('mobile',$this->mobile,true);
		$criteria->compare('role',$this->role,true);
		$criteria->compare('regDate',$this->regDate,true);
		$criteria->compare('actCode',$this->actCode,true);
		$criteria->compare('accStatus',$this->accStatus);
		$criteria->compare('lastLogin',$this->lastLogin,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}

	/**
	 * Returns the static model of the specified AR class.
	 * Please note that you should have this exact method in all your CActiveRecord descendants!
	 * @param string $className active record class name.
	 * @return Login the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}
